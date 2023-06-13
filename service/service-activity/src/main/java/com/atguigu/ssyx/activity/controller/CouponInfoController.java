package com.atguigu.ssyx.activity.controller;


import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get'
    @GetMapping("{page}/{limit}")
    public Result<IPage<CouponInfo>> list(@PathVariable Long page,
                       @PathVariable Long limit) {
        return Result.ok(couponInfoService.selectPageCouponInfo(page,limit));
    }

    //2 添加优惠卷
//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    //3 根据id查询优惠卷
//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @GetMapping("get/{id}")
    public Result<CouponInfo> get(@PathVariable Long id) {
        return Result.ok(couponInfoService.getCouponInfo(id));
    }

    //4 根据优惠卷id查询规则数据
//    url: `${api_name}/findCouponRuleList/${id}`,
//    method: 'get'
    @GetMapping("findCouponRuleList/{id}")
    public Result<Map<String,Object>> findCouponRuleList(@PathVariable Long id) {
        return Result.ok(couponInfoService.findCouponRuleList(id));
    }

    //5 添加优惠卷规则数据
//    url: `${api_name}/saveCouponRule`,
//    method: 'post',
//    data: rule
    @PostMapping("saveCouponRule")
    public Result<Boolean> saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}

