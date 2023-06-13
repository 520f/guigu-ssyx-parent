package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin //跨域
public class IndexController {

    //   http://localhost:8201/admin/acl/index/login
    //1 login登录
   @Operation(description="登录")
    @PostMapping("login")
    public Result<Object>  login() {
        //返回token值
        Map<String,String> map = new HashMap<>();
        map.put("token","token-admin");
        return Result.ok(map);
    }

//    url: '/admin/acl/index/info',
//    method: 'get',
    //2 getInfo 获取信息
   @Operation(description="获取信息")
    @GetMapping("info")
    public Result<Object>  info() {
        Map<String,String> map = new HashMap<>();
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

//    url: '/admin/acl/index/logout',
//    method: 'post'
    //3 logout 退出
   @Operation(description="退出")
    @PostMapping("logout")
    public Result<Object>  logout() {
        return Result.ok(null);
    }
}
