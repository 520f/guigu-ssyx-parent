package com.atguigu.ssyx;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceUserApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceUserApplication.class).web(WebApplicationType.REACTIVE).run(args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }
}
