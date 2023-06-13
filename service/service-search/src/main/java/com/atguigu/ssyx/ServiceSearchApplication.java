package com.atguigu.ssyx;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@EnableFeignClients
@EnableElasticsearchRepositories(basePackages = "com")
public class ServiceSearchApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceSearchApplication.class).web(WebApplicationType.REACTIVE).run(args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }

}
