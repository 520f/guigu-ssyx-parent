package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//    url: `${api_name}/${groupId}`,
//    method: 'get'
    @Operation(description = "根据平台属性分组id查询")
    @GetMapping("{groupId}")
    public Result<List<Attr>> list(@PathVariable Long groupId) {
        return Result.ok(attrService.getAttrListByGroupId(groupId));
    }

    @Operation(description= "获取")
    @GetMapping("get/{id}")
    public Result<Attr> get(@PathVariable Long id) {
        return Result.ok(attrService.getById(id));
    }

    @Operation(description= "新增")
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @Operation(description= "修改")
    @PutMapping("update")
    public Result<Boolean> updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @Operation(description= "删除")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @Operation(description= "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Boolean> batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }
}

