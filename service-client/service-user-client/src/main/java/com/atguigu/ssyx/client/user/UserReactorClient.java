package com.atguigu.ssyx.client.user;

import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class UserReactorClient {

    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-user").build();
    }

    //根据userId查询提货点和团长信息
    public Mono<LeaderAddressVo> getUserAddressByUserId(Long userId){
         return webClient.get()
                .uri("/api/user/leader/inner/getUserAddressByUserId/{userId}", userId)
                .retrieve()
                .bodyToMono(LeaderAddressVo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }
}

