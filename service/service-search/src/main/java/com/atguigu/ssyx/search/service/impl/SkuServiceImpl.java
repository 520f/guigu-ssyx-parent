package com.atguigu.ssyx.search.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.ssyx.activity.client.ActivityReactorClient;
import com.atguigu.ssyx.client.product.ProductReactorClient;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.repository.SkuRepository;
import com.atguigu.ssyx.search.service.SkuService;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductReactorClient productReactorClient;

    @Autowired
    private ActivityReactorClient activityReactorClient;

    @Autowired
    private RedisTemplate redisTemplate;

    //更新商品热度
    @Override
    public void incrHotScore(Long skuId) {
        String key = "hotScore";
        //redis保存数据，每次+1
        Double hotScore = redisTemplate.opsForZSet().incrementScore(key, "skuId:" + skuId, 1);
        //规则
        if (hotScore % 10 == 0) {
            //更新es
            Optional<SkuEs> optional = skuRepository.findById(skuId);
            SkuEs skuEs = optional.get();
            skuEs.setHotScore(Math.round(hotScore));
            skuRepository.save(skuEs);
        }
    }

    //获取爆款商品
    @Override
    public Mono<List<SkuEs>> findHotSkuList() {
        //find  read  get开头
        //关联条件关键字
        // 0代表第一页
        Pageable pageable = PageRequest.of(0, 10);
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        return Mono.just(pageModel.getContent());
    }

    //查询分类商品
    @Override
    public Mono<Page<SkuEs>> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo) {
        //TODO:待测试，理论上所有接口都需要token
        UserLoginVo userLoginVo = JSON.parseObject(StpUtil.getTokenInfo().getTag(), UserLoginVo.class);
        Long wareId = userLoginVo.getWareId();
        //1 向SkuEsQueryVo设置wareId，当前登录用户的仓库id
        skuEsQueryVo.setWareId(wareId);

        Page<SkuEs> pageModel;
        //2 调用SkuRepository方法，根据springData命名规则定义方法，进行条件查询
        //// 判断keyword是否为空，如果为空 ，根据仓库id + 分类id查询
        String keyword = skuEsQueryVo.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            pageModel = skuRepository.findByCategoryIdAndWareId(
                    skuEsQueryVo.getCategoryId(),
                    skuEsQueryVo.getWareId(),
                    pageable);
        } else {
            ///如果keyword不为空根据仓库id + keyword进行查询
            pageModel = skuRepository.findByKeywordAndWareId(
                    skuEsQueryVo.getKeyword(),
                    skuEsQueryVo.getWareId(),
                    pageable);
        }

        //3 查询商品参加优惠活动
        List<SkuEs> skuEsList = pageModel.getContent();

        if (!CollectionUtils.isEmpty(skuEsList)) {
            //遍历skuEsList，得到所有skuId
            List<Long> skuIdList = skuEsList.stream()
                    .map(SkuEs::getId)
                    .collect(Collectors.toList());
            //根据skuId列表远程调用，调用service-activity里面的接口得到数据
            //返回Map<Long,List<String>>
            //// map集合key就是skuId值，Long类型
            //// map集合value是List集合，sku参与活动里面多个规则名称列表
            ///// 一个商品参加一个活动，一个活动里面可以有多个规则
            ////// 比如有活动：中秋节满减活动
            ////// 一个活动可以有多个规则：
            ////// 中秋节满减活动有两个规则：满20元减1元，满58元减5元
            //远程调用
            return activityReactorClient.findActivity(skuIdList).mapNotNull(skuIdToRuleListMap -> {
                //封装获取数据到skuEs里面 ruleList属性里面
                if (!CollectionUtils.isEmpty(skuIdToRuleListMap)) {
                    skuEsList.forEach(skuEs -> {
                        skuEs.setRuleList(skuIdToRuleListMap.get(skuEs.getId()));
                    });
                }
                return pageModel;
            }).switchIfEmpty(Mono.just(pageModel));
        } else {
            return Mono.just(pageModel);
        }
    }

    //上架
    @Override
    public void upperSku(Long skuId) {
        //1 通过远程调用 ，根据skuid获取相关信息
        productReactorClient.getSkuInfo(skuId).map(skuInfo -> {
            //2 获取数据封装SkuEs对象
            return productReactorClient.getCategory(skuInfo.getCategoryId()).mapNotNull(category -> {
                SkuEs skuEs = new SkuEs();
                //封装分类
                if (category != null) {
                    skuEs.setCategoryId(category.getId());
                    skuEs.setCategoryName(category.getName());
                }
                //封装sku信息部分
                skuEs.setId(skuInfo.getId());
                skuEs.setKeyword(skuInfo.getSkuName() + "," + skuEs.getCategoryName());
                skuEs.setWareId(skuInfo.getWareId());
                skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
                skuEs.setImgUrl(skuInfo.getImgUrl());
                skuEs.setTitle(skuInfo.getSkuName());
                //普通商品
                if (Objects.equals(skuInfo.getSkuType(), SkuType.COMMON.getCode())) {
                    skuEs.setSkuType(0);
                    skuEs.setPrice(skuInfo.getPrice().doubleValue());
                    skuEs.setStock(skuInfo.getStock());
                    skuEs.setSale(skuInfo.getSale());
                    skuEs.setPerLimit(skuInfo.getPerLimit());
                }
                //3 调用方法添加ES
                skuRepository.save(skuEs);
                return null;
            });
        }).subscribeOn(Schedulers.parallel()).subscribe();
    }

    //下架
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
