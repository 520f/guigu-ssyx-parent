package com.atguigu.ssyx.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 全局配置类
 *
 * @author XUYALUN
 * @date 2023-02-13 14:31:59
 */
@Log4j2
@Configuration
public class SaTokenConfigure {

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/api/**")
                // 指定 [放行路由]
                .addExclude("/favicon.ico","/api/user/weixin/wxLogin/*")
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    log.info("---------- sa全局认证，访问路径:{}", SaHolder.getRequest().getUrl());
                    StpUtil.checkLogin();
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数 
                .setError(e -> {
                    log.info("---------- sa全局异常");
                    // 设置响应头
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    if (e instanceof NotLoginException) {
                        return JSON.toJSONString(new SaResult(ResultCodeEnum.LOGIN_AUTH.getCode(), ResultCodeEnum.LOGIN_AUTH.getMessage(), null));
                    }
                    return JSON.toJSONString(SaResult.error(e.getMessage()));
                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8")
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "content-type, satoken, *")
                            // 服务器名称
                            .setServer("sa-server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff");
                });
    }

}
