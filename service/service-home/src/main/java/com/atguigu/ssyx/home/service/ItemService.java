package com.atguigu.ssyx.home.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface ItemService {

    //详情
    Mono<Map<String, Object>> item(Long id, Long userId);
}
