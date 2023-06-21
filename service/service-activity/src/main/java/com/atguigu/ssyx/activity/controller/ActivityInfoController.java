package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
//@CrossOrigin
public class ActivityInfoController {

    @Autowired
    private ActivityInfoService activityInfoService;

    //列表
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<ActivityInfo>>> list(@PathVariable Long page,
                                            @PathVariable Long limit) {
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        return activityInfoService.selectPage(pageParam)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("get/{id}")
    public Mono<Result<ActivityInfo>> get(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Mono.just(Result.ok(activityInfo));
    }

    //添加活动
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.save(activityInfo);
        return Mono.just(Result.ok(null));
    }

    //1 根据活动id获取活动规则数据
    @GetMapping("findActivityRuleList/{id}")
    public Mono<Result<Map<String, Object>>> findActivityRuleList(@PathVariable Long id) {
        return activityInfoService.findActivityRuleList(id)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)));
    }

    //2 在活动里面添加规则数据
    @PostMapping("saveActivityRule")
    public Mono<Result<Boolean>> saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRule(activityRuleVo);
        return Mono.just(Result.ok(null));
    }

    //3 根据关键字查询匹配sku信息
//    url: `${api_name}/findSkuInfoByKeyword/${keyword}`,
//    method: 'get'
    @GetMapping("findSkuInfoByKeyword/{keyword}")
    public Mono<Result<List<SkuInfo>>> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return activityInfoService.findSkuInfoByKeyword(keyword)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)));
    }

}

