package com.atguigu.ssyx.cart.client;

import com.atguigu.ssyx.model.order.CartInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
public class CartReactorClient {

    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-cart").build();
    }

    /**
     * 根据用户Id 查询购物车列表
     *
     * @param userId 用户Id
     * @return 购物车列表
     */
    public Mono<List<CartInfo>> getCartCheckedList(Long userId) {
        return webClient.get()
                .uri("/api/cart/inner/getCartCheckedList/{userId}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CartInfo>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }
}
