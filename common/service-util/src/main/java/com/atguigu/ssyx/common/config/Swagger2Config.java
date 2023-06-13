package com.atguigu.ssyx.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2配置信息
 */
@Configuration
public class Swagger2Config {

    private OpenAPI webApiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("网站-API文档")
                .description("本文档描述了尚上优选网站微服务接口定义")
                .version("1.0")
                .contact(null)
                .contact(new Contact()));
    }

    private OpenAPI adminApiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("后台管理系统-API文档")
                .description("本文档描述了尚上优选后台系统服务接口定义")
                .version("1.0")
                .contact(new Contact()));
    }
}
