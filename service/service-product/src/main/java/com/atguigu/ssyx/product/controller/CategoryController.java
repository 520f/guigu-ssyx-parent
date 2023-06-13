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
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @Operation(description = "商品分类列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<Category>> list(@PathVariable Long page,
                       @PathVariable Long limit,
                       CategoryQueryVo categoryQueryVo) {
        Page<Category> pageParam = new Page<>(page,limit);
        return Result.ok(categoryService.selectPageCategory(pageParam,categoryQueryVo));
    }

    @Operation(description =  "获取商品分类信息")
    @GetMapping("get/{id}")
    public Result<Category> get(@PathVariable Long id) {
        return Result.ok(categoryService.getById(id));
    }

    @Operation(description =  "新增商品分类")
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok(null);
    }

    @Operation(description =  "修改商品分类")
    @PutMapping("update")
    public Result<Boolean> updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok(null);
    }

    @Operation(description =  "删除商品分类")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok(null);
    }

    @Operation(description =  "根据id列表删除商品分类")
    @DeleteMapping("batchRemove")
    public Result<Boolean> batchRemove(@RequestBody List<Long> idList) {
        categoryService.removeByIds(idList);
        return Result.ok(null);
    }

    //      url: `${api_name}/findAllList`,
    //      method: 'get'
    //查询所有商品分类
    @Operation(description = "查询所有商品分类")
    @GetMapping("findAllList")
    public Result<List<Category>> findAllList() {
        return Result.ok(categoryService.list());
    }
}

