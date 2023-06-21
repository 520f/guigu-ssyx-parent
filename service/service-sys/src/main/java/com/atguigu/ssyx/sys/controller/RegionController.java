package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/admin/sys/region")
//@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;

    //根据区域关键字查询区域列表信息
    @Operation(description = "根据区域关键字查询区域列表信息")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Mono<Result<List<Region>>> findRegionByKeyword(@PathVariable("keyword") String keyword) {
        return regionService.getRegionByKeyword(keyword)
                .mapNotNull(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }
}

