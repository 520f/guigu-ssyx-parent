package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.atguigu.ssyx.vo.product.SkuStockLockVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInnnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    //根据分类id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Mono<Category> getCategory(@PathVariable Long categoryId) {
        return Mono.just(categoryService.getById(categoryId));
    }

    //根据skuid获取sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public Mono<SkuInfo> getSkuInfo(@PathVariable Long skuId) {
        return Mono.just(skuInfoService.getById(skuId));
    }

    //根据skuId列表得到sku信息列表
    @PostMapping("inner/findSkuInfoList")
    public Mono<List<SkuInfo>> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList).subscribeOn(Schedulers.parallel());
    }

    //根据分类id获取分类列表
    @PostMapping("inner/findCategoryList")
    public Mono<List<Category>> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return Mono.just(categoryService.listByIds(categoryIdList));
    }

    //根据关键字匹配sku列表
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public Mono<List<SkuInfo>> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword).subscribeOn(Schedulers.parallel());
    }

    //获取所有分类
    @GetMapping("inner/findAllCategoryList")
    public Mono<List<Category>> findAllCategoryList() {
        return Mono.just(categoryService.list());
    }

    //获取新人专享商品
    @GetMapping("inner/findNewPersonSkuInfoList")
    public Mono<List<SkuInfo>> findNewPersonSkuInfoList() {
        return skuInfoService.findNewPersonSkuInfoList().subscribeOn(Schedulers.parallel());
    }

    //根据skuId获取sku信息
    @GetMapping("inner/getSkuInfoVo/{skuId}")
    public Mono<SkuInfoVo> getSkuInfoVo(@PathVariable Long skuId) {
        return skuInfoService.getSkuInfoVo(skuId).subscribeOn(Schedulers.parallel());
    }

    //验证和锁定库存
    @Operation(description = "锁定库存")
    @PostMapping("inner/checkAndLock/{orderNo}")
    public Mono<Boolean> checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList,
                                @PathVariable String orderNo) {
        return skuInfoService.checkAndLock(skuStockLockVoList, orderNo).subscribeOn(Schedulers.parallel());
    }
}
