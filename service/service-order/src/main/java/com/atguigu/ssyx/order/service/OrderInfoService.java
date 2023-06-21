package com.atguigu.ssyx.order.service;

import com.atguigu.ssyx.model.order.OrderInfo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.atguigu.ssyx.vo.order.OrderSubmitVo;
import com.atguigu.ssyx.vo.order.OrderUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
public interface OrderInfoService extends IService<OrderInfo> {

    //确认订单
    Mono<OrderConfirmVo> confirmOrder();

    //生成订单
    Mono<Long> submitOrder(OrderSubmitVo orderParamVo);

    //订单详情
    Mono<OrderInfo> getOrderInfoById(Long orderId);

    //根据orderNo查询订单信息
    Mono<OrderInfo> getOrderInfoByOrderNo(String orderNo);

    //订单支付成功，更新订单状态，扣减库存
    void orderPay(String orderNo);

    //订单查询
    Mono<IPage<OrderInfo>> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);
}
