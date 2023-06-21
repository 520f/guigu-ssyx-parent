package com.atguigu.ssyx.order.client;

import com.atguigu.ssyx.model.order.OrderInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class OrderReactorClient {

    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-order").build();
    }

    public Mono<OrderInfo> getOrderInfo(String orderNo) {
         return webClient.get()
                .uri("/api/order/inner/getOrderInfo/{orderNo}", orderNo)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }
}
