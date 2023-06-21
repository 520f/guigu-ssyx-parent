package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/admin/product/attr")
//@CrossOrigin
public class AttrController {

    @Autowired
    private AttrService attrService;

    //平台属性列表方法
    //根据平台属性分组id查询
    @Operation(description = "根据平台属性分组id查询")
    @GetMapping("{groupId}")
    public Mono<Result<List<Attr>>> list(@PathVariable Long groupId) {
        return attrService.getAttrListByGroupId(groupId)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    @Operation(description= "获取")
    @GetMapping("get/{id}")
    public Mono<Result<Attr>> get(@PathVariable Long id) {
        return Mono.just(Result.ok(attrService.getById(id)));
    }

    @Operation(description= "新增")
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Mono.just(Result.ok(null));
    }

    @Operation(description= "修改")
    @PutMapping("update")
    public Mono<Result<Boolean>> updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Mono.just(Result.ok(null));
    }

    @Operation(description= "删除")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Boolean>> remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Mono.just(Result.ok(null));
    }

    @Operation(description= "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Mono<Result<Boolean> > batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Mono.just(Result.ok(null));
    }
}

