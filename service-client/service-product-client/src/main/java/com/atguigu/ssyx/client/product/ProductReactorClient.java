package com.atguigu.ssyx.client.product;

import com.alibaba.fastjson.JSON;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.atguigu.ssyx.vo.product.SkuStockLockVo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
public class ProductReactorClient {

    @Resource(name = "webClientBuilder")
    WebClient.Builder webClientBuilder;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = webClientBuilder.baseUrl("http://service-product").build();
    }

    //根据skuId获取sku信息
    public Mono<SkuInfoVo> getSkuInfoVo(@PathVariable Long skuId) {
        return webClient.get()
                .uri("/api/product/inner/getSkuInfoVo/{skuId}", skuId)
                .retrieve()
                .bodyToMono(SkuInfoVo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //获取新人专享商品
    public Mono<List<SkuInfo>> findNewPersonSkuInfoList() {
        return webClient.get()
                .uri("/api/product/inner/findNewPersonSkuInfoList")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SkuInfo>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //所有分类
    public Mono<List<Category>> findAllCategoryList() {
        return webClient.get()
                .uri("/api/product/inner/findAllCategoryList")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Category>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<Category> getCategory(Long categoryId) {
        return webClient.get()
                .uri("/api/product/inner/getCategory/{categoryId}", categoryId)
                .retrieve()
                .bodyToMono(Category.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<SkuInfo> getSkuInfo(Long skuId) {
        return webClient.get()
                .uri("/api/product/inner/getSkuInfo/{skuId}", skuId)
                .retrieve()
                .bodyToMono(SkuInfo.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //根据skuId列表得到sku信息列表
    public Mono<List<SkuInfo>> findSkuInfoList(List<Long> skuIdList) {
        return webClient.post()
                .uri("/api/product/inner/findSkuInfoList")
                .bodyValue(JSON.toJSONString(skuIdList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SkuInfo>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //根据分类id获取分类列表
    public Mono<List<Category>> findCategoryList(List<Long> categoryIdList) {
        return webClient.post()
                .uri("/api/product/inner/findCategoryList")
                .bodyValue(JSON.toJSONString(categoryIdList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Category>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //根据关键字匹配sku列表
    public Mono<List<SkuInfo>> findSkuInfoByKeyword(String keyword) {
        return webClient.get()
                .uri("/api/product/inner/findSkuInfoByKeyword/{keyword}", keyword)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SkuInfo>>() {
                })
                .cache()
                .subscribeOn(Schedulers.parallel());
    }

    //验证和锁定库存
    public Mono<Boolean> checkAndLock(List<SkuStockLockVo> skuStockLockVoList, String orderNo) {
        return webClient.post()
                .uri("/api/product/inner/checkAndLock/{orderNo}", orderNo)
                .bodyValue(JSON.toJSONString(skuStockLockVoList))
                .retrieve()
                .bodyToMono(Boolean.class)
                .cache()
                .subscribeOn(Schedulers.parallel());
    }
}
