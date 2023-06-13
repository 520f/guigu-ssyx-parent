package com.atguigu.ssyx.home.controller;

import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "首页接口")
@RestController
@RequestMapping("api/home")
public class HomeApiController {

    @Autowired
    private HomeService homeService;

    @Operation(description = "首页数据显示接口")
    @GetMapping("index")
    public Result<Map<String,Object>> index(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        return Result.ok(homeService.homeData(userId));
    }

}
