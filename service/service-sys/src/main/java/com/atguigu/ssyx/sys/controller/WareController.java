package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/admin/sys/ware")
//@CrossOrigin
public class WareController {

    @Autowired
    private WareService wareService;

    //查询所有仓库列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @Operation(description = "查询所有仓库列表")
    @GetMapping("findAllList")
    public Mono<Result<List<Ware>>> findAllList() {
        return Mono.just(Result.ok(wareService.list()));
    }
}

