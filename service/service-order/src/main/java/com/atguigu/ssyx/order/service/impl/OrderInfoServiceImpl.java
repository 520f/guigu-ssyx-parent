package com.atguigu.ssyx.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.atguigu.ssyx.activity.client.ActivityReactorClient;
import com.atguigu.ssyx.cart.client.CartReactorClient;
import com.atguigu.ssyx.client.product.ProductReactorClient;
import com.atguigu.ssyx.client.user.UserReactorClient;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.*;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.order.OrderInfo;
import com.atguigu.ssyx.model.order.OrderItem;
import com.atguigu.ssyx.mq.constant.MqConst;
import com.atguigu.ssyx.mq.service.RabbitService;
import com.atguigu.ssyx.order.mapper.OrderInfoMapper;
import com.atguigu.ssyx.order.mapper.OrderItemMapper;
import com.atguigu.ssyx.order.service.OrderInfoService;
import com.atguigu.ssyx.vo.order.CartInfoVo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.atguigu.ssyx.vo.order.OrderSubmitVo;
import com.atguigu.ssyx.vo.order.OrderUserQueryVo;
import com.atguigu.ssyx.vo.product.SkuStockLockVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private UserReactorClient userReactorClient;

    @Autowired
    private CartReactorClient cartReactorClient;

    @Autowired
    private ActivityReactorClient activityReactorClient;

    @Autowired
    private ProductReactorClient productReactorClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    //确认订单
    @Override
    public Mono<OrderConfirmVo> confirmOrder() {
        //获取用户id
        Long userId = StpUtil.getLoginId(-1L);

        //获取购物车里面选中的商品
        return cartReactorClient.getCartCheckedList(userId).flatMap(cartInfoList -> {
            //唯一标识订单
            String orderNo = String.valueOf(System.currentTimeMillis());
            redisTemplate.opsForValue().set(RedisConst.ORDER_REPEAT + orderNo, orderNo,
                    24, TimeUnit.HOURS);

            //获取购物车满足条件活动和优惠卷信息
            return activityReactorClient.findCartActivityAndCoupon(cartInfoList, userId).flatMap(orderConfirmVo -> {
                //获取用户对应团长信息
                return userReactorClient.getUserAddressByUserId(userId).mapNotNull(leaderAddressVo -> {
                    //封装其他值
                    orderConfirmVo.setLeaderAddressVo(leaderAddressVo);
                    orderConfirmVo.setOrderNo(orderNo);
                    return orderConfirmVo;
                });
            });
        });
    }

    //生成订单
    @Override
    public Mono<Long> submitOrder(OrderSubmitVo orderParamVo) {
        //第一步 设置给哪个用户生成订单  设置orderParamVo的userId
        Long userId = StpUtil.getLoginId(-1L);
        orderParamVo.setUserId(userId);

        //第二步 订单不能重复提交，重复提交验证
        // 通过redis + lua脚本进行判断
        //// lua脚本保证原子性操作
        //1 获取传递过来的订单 orderNo
        String orderNo = orderParamVo.getOrderNo();
        if (StringUtils.isEmpty(orderNo)) {
            throw new SsyxException(ResultCodeEnum.ILLEGAL_REQUEST);
        }

        //2 拿着orderNo 到 redis进行查询，
        String script = "if(redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";
        //3 如果redis有相同orderNo，表示正常提交订单，把redis的orderNo删除
        Boolean flag = (Boolean) redisTemplate
                .execute(new DefaultRedisScript(script, Boolean.class),
                        Arrays.asList(RedisConst.ORDER_REPEAT + orderNo), orderNo);
        //4 如果redis没有相同orderNo，表示重复提交了，不能再往后进行
        if (Boolean.FALSE.equals(flag)) {
            throw new SsyxException(ResultCodeEnum.REPEAT_SUBMIT);
        }

        //第三步 验证库存 并且 锁定库存
        // 比如仓库有10个西红柿，我想买2个西红柿
        // ** 验证库存，查询仓库里面是是否有充足西红柿
        // ** 库存充足，库存锁定 2锁定（目前没有真正减库存）
        //1、远程调用service-cart模块，获取当前用户购物车商品（选中的购物项）
        return cartReactorClient.getCartCheckedList(userId).flatMap(cartInfoList -> {
            //2、购物车有很多商品，商品不同类型，重点处理普通类型商品
            List<CartInfo> commonSkuList = cartInfoList.stream()
                    .filter(cartInfo -> Objects.equals(cartInfo.getSkuType(), SkuType.COMMON.getCode()))
                    .toList();

            //3、把获取购物车里面普通类型商品list集合，
            // List<CartInfo>转换List<SkuStockLockVo>
            if (!CollectionUtils.isEmpty(commonSkuList)) {
                List<SkuStockLockVo> commonStockLockVoList = commonSkuList.stream().map(item -> {
                    SkuStockLockVo skuStockLockVo = new SkuStockLockVo();
                    skuStockLockVo.setSkuId(item.getSkuId());
                    skuStockLockVo.setSkuNum(item.getSkuNum());
                    return skuStockLockVo;
                }).collect(Collectors.toList());

                //4、远程调用service-product模块实现锁定商品
                // 验证库存并锁定库存，保证具备原子性
                return productReactorClient.checkAndLock(commonStockLockVoList, orderNo).flatMap(isLockSuccess -> {
                    //库存锁定失败
                    if (!isLockSuccess) {
                        throw new SsyxException(ResultCodeEnum.ORDER_STOCK_FALL);
                    }
                    //第四步 下单过程
                    //1 向两张表添加数据
                    // order_info 和 order_item
                    Mono<Long> orderIdMono = this.saveOrder(orderParamVo, cartInfoList);

                    Mono.fromRunnable(() -> {
                        //下单完成，删除购物车记录
                        //发送mq消息
                        rabbitService.sendMessage(MqConst.EXCHANGE_ORDER_DIRECT, MqConst.ROUTING_DELETE_CART, orderParamVo.getUserId());
                    }).subscribeOn(Schedulers.parallel()).subscribe();

                    //第五步 返回订单id
                    return orderIdMono;
                }).switchIfEmpty(Mono.error(new SsyxException(ResultCodeEnum.ORDER_STOCK_FALL)));
            }else {
                //第四步 下单过程
                //1 向两张表添加数据
                // order_info 和 order_item
                Mono<Long> orderIdMono = this.saveOrder(orderParamVo, cartInfoList);

                Mono.fromRunnable(() -> {
                    //下单完成，删除购物车记录
                    //发送mq消息
                    rabbitService.sendMessage(MqConst.EXCHANGE_ORDER_DIRECT, MqConst.ROUTING_DELETE_CART, orderParamVo.getUserId());
                }).subscribeOn(Schedulers.parallel()).subscribe();

                //第五步 返回订单id
                return orderIdMono;
            }
        });
    }

    //1 向两张表添加数据
    // order_info 和 order_item
    @Transactional(rollbackFor = {Exception.class})
    public Mono<Long> saveOrder(OrderSubmitVo orderParamVo, List<CartInfo> cartInfoList) {
        if (CollectionUtils.isEmpty(cartInfoList)) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        //查询用户提货点和团长信息
        Long userId = StpUtil.getLoginId(-1L);
        return userReactorClient.getUserAddressByUserId(userId).flatMap(leaderAddressVo -> {
            //计算金额
            //营销活动金额
            return computeActivitySplitAmount(cartInfoList).flatMap(activitySplitAmount -> {
                //优惠卷金额
                return computeCouponInfoSplitAmount(cartInfoList, orderParamVo.getCouponId()).map(couponInfoSplitAmount -> {
                    //封装订单项数据
                    List<OrderItem> orderItemList = new ArrayList<>();
                    for (CartInfo cartInfo : cartInfoList) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setId(null);
                        orderItem.setCategoryId(cartInfo.getCategoryId());
                        if (Objects.equals(cartInfo.getSkuType(), SkuType.COMMON.getCode())) {
                            orderItem.setSkuType(SkuType.COMMON);
                        } else {
                            orderItem.setSkuType(SkuType.SECKILL);
                        }
                        orderItem.setSkuId(cartInfo.getSkuId());
                        orderItem.setSkuName(cartInfo.getSkuName());
                        orderItem.setSkuPrice(cartInfo.getCartPrice());
                        orderItem.setImgUrl(cartInfo.getImgUrl());
                        orderItem.setSkuNum(cartInfo.getSkuNum());
                        orderItem.setLeaderId(orderParamVo.getLeaderId());
                        //营销活动金额
                        BigDecimal activityAmount = activitySplitAmount.get("activity:" + orderItem.getSkuId());
                        if (activityAmount == null) {
                            activityAmount = new BigDecimal(0);
                        }
                        orderItem.setSplitActivityAmount(activityAmount);

                        //优惠卷金额
                        BigDecimal couponAmount = couponInfoSplitAmount.get("coupon:" + orderItem.getSkuId());
                        if (couponAmount == null) {
                            couponAmount = new BigDecimal(0);
                        }
                        orderItem.setSplitCouponAmount(couponAmount);

                        //总金额
                        BigDecimal skuTotalAmount = orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum()));
                        //优惠之后金额
                        BigDecimal splitTotalAmount = skuTotalAmount.subtract(activityAmount).subtract(couponAmount);
                        orderItem.setSplitTotalAmount(splitTotalAmount);
                        orderItemList.add(orderItem);
                    }

                    //封装订单OrderInfo数据
                    OrderInfo orderInfo = new OrderInfo();
                    //用户id
                    orderInfo.setUserId(userId);
                    //订单号 唯一标识
                    orderInfo.setOrderNo(orderParamVo.getOrderNo());
                    //订单状态，生成成功未支付
                    orderInfo.setOrderStatus(OrderStatus.UNPAID);
                    //团长id
                    orderInfo.setLeaderId(orderParamVo.getLeaderId());
                    //团长名称
                    orderInfo.setLeaderName(leaderAddressVo.getLeaderName());

                    orderInfo.setLeaderPhone(leaderAddressVo.getLeaderPhone());
                    orderInfo.setTakeName(leaderAddressVo.getTakeName());
                    orderInfo.setReceiverName(orderParamVo.getReceiverName());
                    orderInfo.setReceiverPhone(orderParamVo.getReceiverPhone());
                    orderInfo.setReceiverProvince(leaderAddressVo.getProvince());
                    orderInfo.setReceiverCity(leaderAddressVo.getCity());
                    orderInfo.setReceiverDistrict(leaderAddressVo.getDistrict());
                    orderInfo.setReceiverAddress(leaderAddressVo.getDetailAddress());
                    orderInfo.setWareId(cartInfoList.get(0).getWareId());
                    orderInfo.setProcessStatus(ProcessStatus.UNPAID);

                    //计算订单金额
                    BigDecimal originalTotalAmount = this.computeTotalAmount(cartInfoList);
                    BigDecimal activityAmount = activitySplitAmount.get("activity:total");

                    if (null == activityAmount) activityAmount = new BigDecimal(0);
                    BigDecimal couponAmount = couponInfoSplitAmount.get("coupon:total");

                    if (null == couponAmount) couponAmount = new BigDecimal(0);
                    BigDecimal totalAmount = originalTotalAmount.subtract(activityAmount).subtract(couponAmount);
                    //计算订单金额
                    orderInfo.setOriginalTotalAmount(originalTotalAmount);
                    orderInfo.setActivityAmount(activityAmount);
                    orderInfo.setCouponAmount(couponAmount);
                    orderInfo.setTotalAmount(totalAmount);

                    //计算团长佣金
                    BigDecimal profitRate = new BigDecimal(0);//orderSetService.getProfitRate();
                    BigDecimal commissionAmount = orderInfo.getTotalAmount().multiply(profitRate);
                    orderInfo.setCommissionAmount(commissionAmount);

                    //添加数据到订单基本信息表
                    baseMapper.insert(orderInfo);

                    //添加订单里面订单项
                    orderItemList.forEach(orderItem -> {
                        orderItem.setOrderId(orderInfo.getId());
                        orderItemMapper.insert(orderItem);
                    });

                    //如果当前订单使用优惠卷，更新优惠卷状态
                    if (orderInfo.getCouponId() != null) {
                        Mono.fromRunnable(() -> {
                            activityReactorClient.updateCouponInfoUseStatus(orderInfo.getCouponId(), userId, orderInfo.getId()).subscribeOn(Schedulers.parallel()).subscribe();
                        }).subscribeOn(Schedulers.parallel()).subscribe();
                    }

                    //下单成功，记录用户购物商品数量，redis
                    //hash类型   key(userId)  -  field(skuId)-value(skuNum)
                    String orderSkuKey = RedisConst.ORDER_SKU_MAP + orderParamVo.getUserId();
                    BoundHashOperations<String, String, Integer> hashOperations = redisTemplate.boundHashOps(orderSkuKey);
                    cartInfoList.forEach(cartInfo -> {
                        if (hashOperations.hasKey(cartInfo.getSkuId().toString())) {
                            Integer orderSkuNum = hashOperations.get(cartInfo.getSkuId().toString()) + cartInfo.getSkuNum();
                            hashOperations.put(cartInfo.getSkuId().toString(), orderSkuNum);
                        }
                    });
                    redisTemplate.expire(orderSkuKey, getCurrentExpireTimes(), TimeUnit.SECONDS);
                    //订单id
                    return orderInfo.getId();
                });
            });
        }).switchIfEmpty(Mono.error(new SsyxException(ResultCodeEnum.DATA_ERROR)));
    }

    private long getCurrentExpireTimes() {
        return DateUtil.endOfDay(new Date()).getTime() - System.currentTimeMillis();
    }

    //订单详情
    @Override
    public Mono<OrderInfo> getOrderInfoById(Long orderId) {
        //根据orderId查询订单基本信息
        OrderInfo orderInfo = baseMapper.selectById(orderId);

        //根据orderId查询订单所有订单项list列表
        List<OrderItem> orderItemList = orderItemMapper.selectList(
                new LambdaQueryWrapper<OrderItem>()
                        .eq(OrderItem::getOrderId, orderInfo.getId())
        );

        //查询所有订单项封装到每个订单对象里面
        orderInfo.setOrderItemList(orderItemList);
        return Mono.justOrEmpty(orderInfo);
    }

    //根据orderNo查询订单信息
    @Override
    public Mono<OrderInfo> getOrderInfoByOrderNo(String orderNo) {
        return Mono.justOrEmpty(baseMapper.selectOne(
                new LambdaQueryWrapper<OrderInfo>()
                        .eq(OrderInfo::getOrderNo, orderNo)
        ));
    }

    //订单支付成功，更新订单状态，扣减库存
    @Override
    public void orderPay(String orderNo) {
        //查询订单状态是否已经修改完成了支付状态
        getOrderInfoByOrderNo(orderNo).mapNotNull(orderInfo->{
            if (orderInfo == null || orderInfo.getOrderStatus() != OrderStatus.UNPAID) {
                return null;
            }
            //更新状态
            this.updateOrderStatus(orderInfo.getId());

            //扣减库存
            rabbitService.sendMessage(MqConst.EXCHANGE_ORDER_DIRECT,
                    MqConst.ROUTING_MINUS_STOCK,
                    orderNo);
            return null;
        });
    }

    //订单查询
    @Override
    public Mono<IPage<OrderInfo>> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam,
                                                     OrderUserQueryVo orderUserQueryVo) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, orderUserQueryVo.getUserId());
        wrapper.eq(OrderInfo::getOrderStatus, orderUserQueryVo.getOrderStatus());
        IPage<OrderInfo> pageModel = baseMapper.selectPage(pageParam, wrapper);

        //获取每个订单，把每个订单里面订单项查询封装
        List<OrderInfo> orderInfoList = pageModel.getRecords();
        for (OrderInfo orderInfo : orderInfoList) {
            //根据订单id查询里面所有订单项列表
            List<OrderItem> orderItemList = orderItemMapper.selectList(
                    new LambdaQueryWrapper<OrderItem>()
                            .eq(OrderItem::getOrderId, orderInfo.getId())
            );
            //把订单项集合封装到每个订单里面
            orderInfo.setOrderItemList(orderItemList);
            //封装订单状态名称
            orderInfo.getParam().put("orderStatusName", orderInfo.getOrderStatus().getComment());
        }
        return Mono.just(pageModel);
    }

    //更新状态
    private void updateOrderStatus(Long id) {
        OrderInfo orderInfo = baseMapper.selectById(id);
        orderInfo.setOrderStatus(OrderStatus.WAITING_DELEVER);
        orderInfo.setProcessStatus(ProcessStatus.WAITING_DELEVER);
        baseMapper.updateById(orderInfo);
    }

    //计算总金额
    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal(0);
        for (CartInfo cartInfo : cartInfoList) {
            BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
            total = total.add(itemTotal);
        }
        return total;
    }

    /**
     * 计算购物项分摊的优惠减少金额
     * 打折：按折扣分担
     * 现金：按比例分摊
     */
    private Mono<Map<String, BigDecimal>> computeActivitySplitAmount(List<CartInfo> cartInfoParamList) {
        Map<String, BigDecimal> activitySplitAmountMap = new HashMap<>();
        //促销活动相关信息
        return activityReactorClient.findCartActivityList(cartInfoParamList).mapNotNull(cartInfoVoList -> {
            //活动总金额
            BigDecimal activityReduceAmount = new BigDecimal(0);
            for (CartInfoVo cartInfoVo : cartInfoVoList) {
                ActivityRule activityRule = cartInfoVo.getActivityRule();
                List<CartInfo> cartInfoList = cartInfoVo.getCartInfoList();
                if (null != activityRule) {
                    //优惠金额， 按比例分摊
                    BigDecimal reduceAmount = activityRule.getReduceAmount();
                    activityReduceAmount = activityReduceAmount.add(reduceAmount);
                    if (cartInfoList.size() == 1) {
                        activitySplitAmountMap.put("activity:" + cartInfoList.get(0).getSkuId(), reduceAmount);
                    } else {
                        //总金额
                        BigDecimal originalTotalAmount = new BigDecimal(0);
                        for (CartInfo cartInfo : cartInfoList) {
                            BigDecimal skuTotalAmount = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                            originalTotalAmount = originalTotalAmount.add(skuTotalAmount);
                        }
                        //记录除最后一项是所有分摊金额， 最后一项=总的 - skuPartReduceAmount
                        BigDecimal skuPartReduceAmount = new BigDecimal(0);
                        if (activityRule.getActivityType() == ActivityType.FULL_REDUCTION) {
                            for (int i = 0, len = cartInfoList.size(); i < len; i++) {
                                CartInfo cartInfo = cartInfoList.get(i);
                                if (i < len - 1) {
                                    BigDecimal skuTotalAmount = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                                    //sku分摊金额
                                    BigDecimal skuReduceAmount = skuTotalAmount.divide(originalTotalAmount, 2, RoundingMode.HALF_UP).multiply(reduceAmount);
                                    activitySplitAmountMap.put("activity:" + cartInfo.getSkuId(), skuReduceAmount);

                                    skuPartReduceAmount = skuPartReduceAmount.add(skuReduceAmount);
                                } else {
                                    BigDecimal skuReduceAmount = reduceAmount.subtract(skuPartReduceAmount);
                                    activitySplitAmountMap.put("activity:" + cartInfo.getSkuId(), skuReduceAmount);
                                }
                            }
                        } else {
                            for (int i = 0, len = cartInfoList.size(); i < len; i++) {
                                CartInfo cartInfo = cartInfoList.get(i);
                                if (i < len - 1) {
                                    BigDecimal skuTotalAmount = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));

                                    //sku分摊金额
                                    BigDecimal skuDiscountTotalAmount = skuTotalAmount.multiply(activityRule.getBenefitDiscount().divide(new BigDecimal("10")));
                                    BigDecimal skuReduceAmount = skuTotalAmount.subtract(skuDiscountTotalAmount);
                                    activitySplitAmountMap.put("activity:" + cartInfo.getSkuId(), skuReduceAmount);

                                    skuPartReduceAmount = skuPartReduceAmount.add(skuReduceAmount);
                                } else {
                                    BigDecimal skuReduceAmount = reduceAmount.subtract(skuPartReduceAmount);
                                    activitySplitAmountMap.put("activity:" + cartInfo.getSkuId(), skuReduceAmount);
                                }
                            }
                        }
                    }
                }
            }
            activitySplitAmountMap.put("activity:total", activityReduceAmount);
            return activitySplitAmountMap;
        }).switchIfEmpty(Mono.just(activitySplitAmountMap));
    }

    //优惠卷优惠金额
    private Mono<Map<String, BigDecimal>> computeCouponInfoSplitAmount(List<CartInfo> cartInfoList, Long couponId) {
        Map<String, BigDecimal> couponInfoSplitAmountMap = new HashMap<>();

        if (null == couponId) {
            return Mono.just(couponInfoSplitAmountMap);
        }

        return activityReactorClient.findRangeSkuIdList(cartInfoList, couponId).mapNotNull(couponInfo -> {
            //sku对应的订单明细
            Map<Long, CartInfo> skuIdToCartInfoMap = new HashMap<>();
            for (CartInfo cartInfo : cartInfoList) {
                skuIdToCartInfoMap.put(cartInfo.getSkuId(), cartInfo);
            }
            //优惠券对应的skuId列表
            List<Long> skuIdList = couponInfo.getSkuIdList();
            if (CollectionUtils.isEmpty(skuIdList)) {
                return couponInfoSplitAmountMap;
            }
            //优惠券优化总金额
            BigDecimal reduceAmount = couponInfo.getAmount();
            if (skuIdList.size() == 1) {
                //sku的优化金额
                couponInfoSplitAmountMap.put("coupon:" + skuIdToCartInfoMap.get(skuIdList.get(0)).getSkuId(), reduceAmount);
            } else {
                //总金额
                BigDecimal originalTotalAmount = new BigDecimal(0);
                for (Long skuId : skuIdList) {
                    CartInfo cartInfo = skuIdToCartInfoMap.get(skuId);
                    BigDecimal skuTotalAmount = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                    originalTotalAmount = originalTotalAmount.add(skuTotalAmount);
                }
                //记录除最后一项是所有分摊金额， 最后一项=总的 - skuPartReduceAmount
                BigDecimal skuPartReduceAmount = new BigDecimal(0);
                if (couponInfo.getCouponType() == CouponType.CASH || couponInfo.getCouponType() == CouponType.FULL_REDUCTION) {
                    for (int i = 0, len = skuIdList.size(); i < len; i++) {
                        CartInfo cartInfo = skuIdToCartInfoMap.get(skuIdList.get(i));
                        if (i < len - 1) {
                            BigDecimal skuTotalAmount = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                            //sku分摊金额
                            BigDecimal skuReduceAmount = skuTotalAmount.divide(originalTotalAmount, 2, RoundingMode.HALF_UP).multiply(reduceAmount);
                            couponInfoSplitAmountMap.put("coupon:" + cartInfo.getSkuId(), skuReduceAmount);

                            skuPartReduceAmount = skuPartReduceAmount.add(skuReduceAmount);
                        } else {
                            BigDecimal skuReduceAmount = reduceAmount.subtract(skuPartReduceAmount);
                            couponInfoSplitAmountMap.put("coupon:" + cartInfo.getSkuId(), skuReduceAmount);
                        }
                    }
                }
            }
            couponInfoSplitAmountMap.put("coupon:total", couponInfo.getAmount());
            return couponInfoSplitAmountMap;
        }).switchIfEmpty(Mono.just(couponInfoSplitAmountMap));
    }
}
