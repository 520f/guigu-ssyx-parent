package com.atguigu.ssyx.home.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface HomeService {

    //首页数据显示接口
    Mono<Map<String, Object>> homeData(Long userId);
}
