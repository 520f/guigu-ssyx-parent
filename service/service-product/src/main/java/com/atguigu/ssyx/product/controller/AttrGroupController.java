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

//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @Operation(description="平台属性分组列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<AttrGroup>> list(@PathVariable Long page,
                       @PathVariable Long limit,
                       AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> pageParam = new Page<>(page,limit);
        return Result.ok(attrGroupService.selectPageAttrGroup(pageParam,attrGroupQueryVo));
    }

    //查询所有平台属性分组列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @Operation(description="查询所有平台属性分组列表")
    @GetMapping("findAllList")
    public Result<List<AttrGroup>> findAllList() {
        return Result.ok(attrGroupService.findAllListAttrGroup());
    }

    @Operation(description= "获取")
    @GetMapping("get/{id}")
    public Result<AttrGroup> get(@PathVariable Long id) {
        return Result.ok(attrGroupService.getById(id));
    }

    @Operation(description = "新增")
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok(null);
    }

    @Operation(description = "修改")
    @PutMapping("update")
    public Result<Boolean> updateById(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok(null);
    }

    @Operation(description = "删除")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.ok(null);
    }

    @Operation(description = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Boolean> batchRemove(@RequestBody List<Long> idList) {
        attrGroupService.removeByIds(idList);
        return Result.ok(null);
    }
}

