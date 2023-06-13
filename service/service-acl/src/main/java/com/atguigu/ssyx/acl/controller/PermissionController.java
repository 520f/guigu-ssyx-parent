package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Tag(name = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //查询所有菜单
//    url: `${api_name}`,
//    method: 'get'
    @Operation(description="查询所有菜单")
    @GetMapping
    public Result<List<Permission>> list() {
        return Result.ok(permissionService.queryAllPermission());
    }

    //添加菜单
//    url: `${api_name}/save`,
//    method: "post",
//    data: permission
    @Operation(description="添加菜单")
    @PostMapping("save")
    public Result<Boolean> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    //修改菜单
//    url: `${api_name}/update`,
//    method: "put",
//    data: permission
    @Operation(description="修改菜单")
    @PutMapping("update")
    public Result<Boolean> update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    //递归删除菜单
//    url: `${api_name}/remove/${id}`,
//    method: "delete"
    @Operation(description="递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }
}
