package com.atguigu.ssyx.activity.controller;


import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
//@CrossOrigin
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    //1 优惠卷分页查询
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<CouponInfo>>> list(@PathVariable Long page,@PathVariable Long limit) {
        return couponInfoService.selectPageCouponInfo(page, limit)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)));
    }

    //2 添加优惠卷
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Mono.just(Result.ok(null));
    }

    //3 根据id查询优惠卷
    @GetMapping("get/{id}")
    public Mono<Result<CouponInfo>> get(@PathVariable Long id) {
        return Mono.just(Result.ok(couponInfoService.getCouponInfo(id)));
    }

    //4 根据优惠卷id查询规则数据
    @GetMapping("findCouponRuleList/{id}")
    public Mono<Result<Map<String, Object>>> findCouponRuleList(@PathVariable Long id) {
        return couponInfoService.findCouponRuleList(id)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)));
    }

    //5 添加优惠卷规则数据
    @PostMapping("saveCouponRule")
    public Mono<Result<Boolean>> saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Mono.just(Result.ok(null));
    }
}

