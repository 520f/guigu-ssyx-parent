package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
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
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/admin/product/category")
//@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //商品分类列表
    @Operation(description = "商品分类列表")
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<Category>>> list(@PathVariable Long page,
                     @PathVariable Long limit,
                     CategoryQueryVo categoryQueryVo) {
        Page<Category> pageParam = new Page<>(page,limit);
        return categoryService.selectPageCategory(pageParam,categoryQueryVo)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    @Operation(description =  "获取商品分类信息")
    @GetMapping("get/{id}")
    public Mono<Result<Category>> get(@PathVariable Long id) {
        return Mono.just(Result.ok(categoryService.getById(id)));
    }

    @Operation(description =  "新增商品分类")
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody Category category) {
        categoryService.save(category);
        return Mono.just(Result.ok(null));
    }

    @Operation(description =  "修改商品分类")
    @PutMapping("update")
    public Mono<Result<Boolean>> updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        return Mono.just(Result.ok(null));
    }

    @Operation(description =  "删除商品分类")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Boolean>> remove(@PathVariable Long id) {
        categoryService.removeById(id);
        return Mono.just(Result.ok(null));
    }

    @Operation(description =  "根据id列表删除商品分类")
    @DeleteMapping("batchRemove")
    public Mono<Result<Boolean>> batchRemove(@RequestBody List<Long> idList) {
        categoryService.removeByIds(idList);
        return Mono.just(Result.ok(null));
    }

    //查询所有商品分类
    @Operation(description = "查询所有商品分类")
    @GetMapping("findAllList")
    public Mono<Result<List<Category>>> findAllList() {
        return  Mono.just(Result.ok(categoryService.list()));
    }
}

