package com.atguigu.ssyx.home.controller;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "商品分类")
@RestController
@RequestMapping("api/home")
public class CategoryApiController {

    @Autowired
    private ProductFeignClient productFeignClient;

    //查询所有分类
    @GetMapping("category")
    public Result<List<Category>> categoryList() {
        return Result.ok(productFeignClient.findAllCategoryList());
    }
}
