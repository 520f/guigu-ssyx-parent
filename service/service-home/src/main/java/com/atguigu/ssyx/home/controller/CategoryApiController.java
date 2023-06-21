package com.atguigu.ssyx.home.controller;

import com.atguigu.ssyx.client.product.ProductReactorClient;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Tag(name = "商品分类")
@RestController
@RequestMapping("api/home")
public class CategoryApiController {

    @Autowired
    private ProductReactorClient productReactorClient;

    //查询所有分类
    @GetMapping("category")
    public Mono<Result<List<Category>>> categoryList() {
        return productReactorClient.findAllCategoryList()
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }
}
