package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin //跨域
public class IndexController {

    //   http://localhost:8201/admin/acl/index/login
    //1 login登录
    @Operation(description = "登录")
    @PostMapping("login")
    public Mono<Result<Map<String, String>>> login() {
        //返回token值
        return Mono.just(Result.ok(new HashMap<>() {{
            put("token", "token-admin");
        }}));
    }

    //2 getInfo 获取信息
    @Operation(description = "获取信息")
    @GetMapping("info")
    public Mono<Result<Map<String, String>>> info() {
        return Mono.just(Result.ok(new HashMap<>() {{
            put("name", "admin");
            put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        }}));
    }

    //3 logout 退出
    @Operation(description = "退出")
    @PostMapping("logout")
    public Mono<Result<Object>> logout() {
        return Mono.just(Result.ok(null));
    }
}
