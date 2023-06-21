package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/admin/product/skuInfo")
//@CrossOrigin
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    //sku列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @Operation(description = "sku列表")
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<SkuInfo>>> list(@PathVariable Long page,
                     @PathVariable Long limit,
                     SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page,limit);
        return skuInfoService.selectPageSkuInfo(pageParam,skuInfoQueryVo)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    //添加商品sku信息
//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @Operation(description = "添加商品sku信息")
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @Operation(description = "获取sku信息")
    @GetMapping("get/{id}")
    public Result<SkuInfoVo> get(@PathVariable Long id) {
        return Result.ok(skuInfoService.getSkuInfo(id));
    }

//    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @Operation(description = "修改sku")
    @PutMapping("update")
    public Result<Boolean> update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @Operation(description =  "删除")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(null);
    }

    @Operation(description =  "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Boolean> batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }

//    url: `${api_name}/check/${id}/${status}`,
//    method: 'get'
    @Operation(description = "商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result<Boolean> check(@PathVariable Long skuId,
                        @PathVariable Integer status) {
        skuInfoService.check(skuId,status);
        return Result.ok(null);
    }

//    url: `${api_name}/publish/${id}/${status}`,
//    method: 'get'
    @Operation(description = "商品上下架")
    @GetMapping("publish/{skuId}/{status}")
    public Result<Boolean> publish(@PathVariable Long skuId,
                          @PathVariable Integer status) {
        skuInfoService.publish(skuId,status);
        return Result.ok(null);
    }

    //新人专享
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result<Boolean> isNewPerson(@PathVariable("skuId") Long skuId,
                              @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok(null);
    }
}

