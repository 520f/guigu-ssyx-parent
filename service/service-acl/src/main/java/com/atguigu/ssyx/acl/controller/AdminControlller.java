package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.acl.utils.MD5;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class AdminControlller {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    //为用户进行分配
    //参数有用户id 和 多个角色id
    @Operation(description="为用户进行角色分配")
    @PostMapping("doAssign")
    public Mono<Result<Object>> doAssign(@RequestParam Long adminId,
                         @RequestParam Long[] roleId) {
        roleService.saveAdminRole(adminId,roleId);
        return Mono.just(Result.ok(null));
    }


    //获取所有角色，和根据用户id查询用户分配角色列表
    @Operation(description="获取用户角色")
    @GetMapping("toAssign/{adminId}")
    public Mono<Result<Map<String, Object>>> toAssign(@PathVariable Long adminId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        return roleService.getRoleByAdminId(adminId).map(Result::ok).subscribeOn(Schedulers.boundedElastic());
    }

    //1 用户列表
    @Operation(description="用户列表")
    @GetMapping("{current}/{limit}")
    public Mono<Result<IPage<Admin>>> list(@PathVariable Long current,@PathVariable Long limit, AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<Admin>(current,limit);
        return adminService.selectPageUser(pageParam, adminQueryVo).map(Result::ok).subscribeOn(Schedulers.boundedElastic());
    }

    //2 id查询用户
    @Operation(description="根据id查询")
    @GetMapping("get/{id}")
    public Mono<Result<Admin>> get(@PathVariable Long id) {
        return Mono.just(Result.ok(adminService.getById(id)));
    }

    //3 添加用户
    @Operation(description="添加用户")
    @PostMapping("save")
    public Mono<Result<Object>> save(@RequestBody Admin admin) {
        //获取输入的密码
        String password = admin.getPassword();
        //对输入密码进行加密 MD5
        String passwordMD5 = MD5.encrypt(password);
        //设置到admin对象里面
        admin.setPassword(passwordMD5);
        //调用方法添加
        adminService.save(admin);
        return Mono.just(Result.ok(null));
    }

    //4 修改用户
    @Operation(description="修改用户")
    @PutMapping("update")
    public Mono<Result<Object>> update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Mono.just(Result.ok(null));
    }

    //5 id删除
    @Operation(description="根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Object>> remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Mono.just(Result.ok(null));
    }

    //6 批量删除
    @Operation(description="批量删除")
    @DeleteMapping("batchRemove")
    public Mono<Result<Object>> batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Mono.just(Result.ok(null));
    }

}
