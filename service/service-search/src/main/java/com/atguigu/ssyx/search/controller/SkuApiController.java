package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.service.SkuService;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    //查询分类商品
    @GetMapping("{page}/{limit}")
    public Mono<Result<Page<SkuEs>>> listSku(@PathVariable Integer page,@PathVariable Integer limit,SkuEsQueryVo skuEsQueryVo) {
        //创建pageable对象，0代表第一页
        Pageable pageable = PageRequest.of(page-1,limit);
        return skuService.search(pageable, skuEsQueryVo)
                .mapNotNull(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    //上架
    @GetMapping("inner/upperSku/{skuId}")
    public Mono<Result<Boolean>> upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Mono.just(Result.ok(null));
    }

    //下架
    @GetMapping("inner/lowerSku/{skuId}")
    public Mono<Result<Boolean>> lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Mono.just(Result.ok(null));
    }

    //获取爆款商品
    @GetMapping("inner/findHotSkuList")
    public Mono<List<SkuEs>> findHotSkuList() {
        return skuService.findHotSkuList().subscribeOn(Schedulers.parallel());
    }

    //更新商品热度
    @GetMapping("inner/incrHotScore/{skuId}")
    public Mono<Boolean> incrHotScore(@PathVariable("skuId") Long skuId) {
        skuService.incrHotScore(skuId);
        return Mono.just(true);
    }
}
