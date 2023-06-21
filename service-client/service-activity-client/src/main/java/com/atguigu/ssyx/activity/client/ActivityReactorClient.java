package com.atguigu.ssyx.activity.client;

import com.alibaba.fastjson.JSON;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.vo.order.CartInfoVo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

@Component
public class ActivityReactorClient {
    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-activity").build();
    }

    //获取购物车里面满足条件优惠卷和活动的信息
    public Mono<OrderConfirmVo> findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId) {
        return webClient.post()
                .uri("/api/activity/inner/findCartActivityAndCoupon/{userId}", userId)
                .bodyValue(JSON.toJSONString(cartInfoList))
                .retrieve()
                .bodyToMono(OrderConfirmVo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "根据skuID获取营销数据和优惠卷")
    public Mono<Map<String, Object>> findActivityAndCoupon(Long skuId, Long userId) {
        return webClient.get()
                .uri("/api/activity/inner/findActivityAndCoupon/{skuId}/{userId}", skuId, userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }


    public Mono<Map<Long, List<String>>> findActivity(List<Long> skuIdList) {
        return webClient.post()
                .uri("/api/activity/inner/findActivity")
                .bodyValue(JSON.toJSONString(skuIdList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, List<String>>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<CouponInfo> findRangeSkuIdList(List<CartInfo> cartInfoList, Long couponId) {
        return webClient.post()
                .uri("/api/activity/inner/findRangeSkuIdList/{couponId}", couponId)
                .bodyValue(JSON.toJSONString(cartInfoList))
                .retrieve()
                .bodyToMono(CouponInfo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<Boolean> updateCouponInfoUseStatus(Long couponId, Long userId, Long orderId) {
        return webClient.get()
                .uri("/api/activity/inner/updateCouponInfoUseStatus/{couponId}/{userId}/{orderId}", couponId, userId, orderId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }


    //获取购物车对应规则数据
    public Mono<List<CartInfoVo>> findCartActivityList(@RequestBody List<CartInfo> cartInfoList) {
        return webClient.post()
                .uri("/api/activity/inner/findCartActivityList")
                .bodyValue(JSON.toJSONString(cartInfoList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CartInfoVo>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

}
