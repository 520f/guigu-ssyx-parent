package com.atguigu.ssyx.activity.api;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.vo.order.CartInfoVo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
public class ActivityInfoApiController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private CouponInfoService couponInfoService;

    //获取购物车里面满足条件优惠卷和活动的信息
    @PostMapping("inner/findCartActivityAndCoupon/{userId}")
    public Mono<OrderConfirmVo> findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList,
                                                    @PathVariable("userId") Long userId) {
        return activityInfoService.findCartActivityAndCoupon(cartInfoList,userId).subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "根据skuId列表获取促销信息")
    @PostMapping("inner/findActivity")
    public Mono<Map<Long, List<String>>> findActivity(@RequestBody List<Long> skuIdList) {
        return activityInfoService.findActivity(skuIdList).subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "根据skuID获取营销数据和优惠卷")
    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    public Mono<Map<String, Object>> findActivityAndCoupon(@PathVariable Long skuId,
                                                           @PathVariable Long userId) {
        return activityInfoService.findActivityAndCoupon(skuId,userId).subscribeOn(Schedulers.parallel());
    }

    //获取购物车对应规则数据
    @PostMapping("inner/findCartActivityList")
    public Mono<List<CartInfoVo>> findCartActivityList(@RequestBody List<CartInfo> cartInfoList) {
        return activityInfoService.findCartActivityList(cartInfoList).subscribeOn(Schedulers.parallel());
    }

    //获取购物车对应优惠卷
    @PostMapping("inner/findRangeSkuIdList/{couponId}")
    public Mono<CouponInfo> findRangeSkuIdList(@RequestBody List<CartInfo> cartInfoList,@PathVariable("couponId") Long couponId) {
        return couponInfoService.findRangeSkuIdList(cartInfoList,couponId).subscribeOn(Schedulers.parallel());
    }

    //更新优惠卷使用状态
    @GetMapping("inner/updateCouponInfoUseStatus/{couponId}/{userId}/{orderId}")
    public Mono<Boolean> updateCouponInfoUseStatus(@PathVariable("couponId") Long couponId, @PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
        couponInfoService.updateCouponInfoUseStatus(couponId,userId,orderId);
        return Mono.just(true);
    }
}
