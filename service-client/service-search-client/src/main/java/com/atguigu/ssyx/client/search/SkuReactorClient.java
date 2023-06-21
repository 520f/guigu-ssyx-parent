package com.atguigu.ssyx.client.search;

import com.atguigu.ssyx.model.search.SkuEs;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
public class SkuReactorClient {

    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-search").build();
    }

    //更新商品热度
    public Mono<Boolean> incrHotScore(Long skuId) {
        return webClient.get()
                .uri("/api/search/sku/inner/incrHotScore/{skuId}", skuId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<List<SkuEs>> findHotSkuList() {
        return webClient.get()
                .uri("/api/search/sku/inner/findHotSkuList")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SkuEs>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }
}
