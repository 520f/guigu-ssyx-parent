package com.atguigu.ssyx.home.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.home.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Tag(name = "商品详情")
@RestController
@RequestMapping("api/home")
public class ItemApiController {

    @Autowired
    private ItemService itemService;

    @GetMapping("item/{id}")
    public Mono<Result<Map<String,Object>>> index(@PathVariable Long id) {
        Long userId = StpUtil.getLoginId(-1L);
        return itemService.item(id, userId).map(Result::ok).subscribeOn(Schedulers.parallel());
    }
}
