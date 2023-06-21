package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
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
 * 属性分组 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/admin/product/attrGroup")
//@CrossOrigin
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    @Operation(description="平台属性分组列表")
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<AttrGroup>>> list(@PathVariable Long page,
                     @PathVariable Long limit,
                     AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> pageParam = new Page<>(page,limit);
        return attrGroupService.selectPageAttrGroup(pageParam,attrGroupQueryVo)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    //查询所有平台属性分组列表
    @Operation(description="查询所有平台属性分组列表")
    @GetMapping("findAllList")
    public Mono<Result<List<AttrGroup>>> findAllList() {
        return attrGroupService.findAllListAttrGroup()
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    @Operation(description= "获取")
    @GetMapping("get/{id}")
    public Mono<Result<AttrGroup>> get(@PathVariable Long id) {
        return Mono.just(Result.ok(attrGroupService.getById(id)));
    }

    @Operation(description = "新增")
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Mono.just(Result.ok(null));
    }

    @Operation(description = "修改")
    @PutMapping("update")
    public Mono<Result<Boolean>> updateById(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Mono.just(Result.ok(null));
    }

    @Operation(description = "删除")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Boolean>> remove(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Mono.just(Result.ok(null));
    }

    @Operation(description = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Mono<Result<Boolean>> batchRemove(@RequestBody List<Long> idList) {
        attrGroupService.removeByIds(idList);
        return Mono.just(Result.ok(null));
    }
}

