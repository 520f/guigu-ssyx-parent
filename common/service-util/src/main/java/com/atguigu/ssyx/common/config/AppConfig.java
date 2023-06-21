package com.atguigu.ssyx.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * 程序配置
 *
 * @author xuyalun
 * @date 2023-02-02 14:03:19
 **/
@Configuration
public class AppConfig implements WebFluxConfigurer {

    @LoadBalanced
    @Bean(name = "webClientBuilder")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "noLbWebClientBuilder")
    public WebClient.Builder noLbWebClientBuilder() {
        return WebClient.builder();
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
                        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .timeZone(TimeZone.getTimeZone(ZoneOffset.ofHours(8))).build();
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
    }

}
