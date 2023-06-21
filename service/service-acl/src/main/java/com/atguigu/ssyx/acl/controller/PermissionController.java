package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Tag(name = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //查询所有菜单
    @Operation(description="查询所有菜单")
    @GetMapping
    public Mono<Result<List<Permission>>> list() {
        return permissionService.queryAllPermission().map(Result::ok).subscribeOn(Schedulers.boundedElastic());
    }

    //添加菜单
    @Operation(description="添加菜单")
    @PostMapping("save")
    public Mono<Result<Boolean>> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Mono.just(Result.ok(null));
    }

    //修改菜单
    @Operation(description="修改菜单")
    @PutMapping("update")
    public Mono<Result<Boolean>> update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Mono.just(Result.ok(null));
    }

    //递归删除菜单
    @Operation(description="递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Boolean>> remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Mono.just(Result.ok(null));
    }
}
