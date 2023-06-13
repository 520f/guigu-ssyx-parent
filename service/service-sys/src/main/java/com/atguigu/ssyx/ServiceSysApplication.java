package com.atguigu.ssyx;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.atguigu.ssyx.*.mapper")
public class ServiceSysApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceSysApplication.class).web(WebApplicationType.REACTIVE).run(args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }
}
