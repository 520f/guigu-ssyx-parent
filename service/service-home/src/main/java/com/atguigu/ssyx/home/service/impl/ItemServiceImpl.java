package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.activity.client.ActivityReactorClient;
import com.atguigu.ssyx.client.product.ProductReactorClient;
import com.atguigu.ssyx.client.search.SkuReactorClient;
import com.atguigu.ssyx.home.service.ItemService;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductReactorClient productReactorClient;

    @Autowired
    private ActivityReactorClient activityReactorClient;

    @Autowired
    private SkuReactorClient skuReactorClient;

    //详情
    @Override
    public Mono<Map<String, Object>> item(Long skuId, Long userId) {
        Map<String, Object> result = new HashMap<>();

        //skuId查询,远程调用获取sku对应数据
        Mono<SkuInfoVo> skuInfoVoMono = productReactorClient.getSkuInfoVo(skuId);

        //sku对应优惠卷信息
        Mono<Map<String, Object>> activityMapMono = activityReactorClient.findActivityAndCoupon(skuId, userId);

        //更新商品热度,远程调用更新热度
        Mono.fromRunnable(() -> {
            skuReactorClient.incrHotScore(skuId).subscribeOn(Schedulers.parallel()).subscribe();
        }).subscribeOn(Schedulers.parallel()).subscribe();

        return Mono.zip(skuInfoVoMono, activityMapMono.switchIfEmpty(Mono.just(new HashMap<>()))).map(tuple -> {
                    SkuInfoVo skuInfoVo = tuple.getT1();
                    result.put("skuInfoVo", skuInfoVo);
                    Map<String, Object> activityMap = tuple.getT2();
                    result.putAll(activityMap);
                    return result;
                })
                .switchIfEmpty(Mono.just(result))
                .subscribeOn(Schedulers.parallel());
    }
}
