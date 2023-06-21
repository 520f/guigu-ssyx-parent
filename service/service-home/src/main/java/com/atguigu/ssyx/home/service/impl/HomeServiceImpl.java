package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.client.product.ProductReactorClient;
import com.atguigu.ssyx.client.search.SkuReactorClient;
import com.atguigu.ssyx.client.user.UserReactorClient;
import com.atguigu.ssyx.home.service.HomeService;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserReactorClient userReactorClient;

    @Autowired
    private ProductReactorClient productReactorClient;

    @Autowired
    private SkuReactorClient skuReactorClient;

    //首页数据显示接口
    @Override
    public Mono<Map<String, Object>> homeData(Long userId) {

        Map<String,Object> result = new HashMap<>();
        //1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        Mono<LeaderAddressVo> leaderAddressVoMono = userReactorClient.getUserAddressByUserId(userId);

        //2 获取所有分类
        // 远程调用service-product模块接口
        Mono<List<Category>> categoryListMono = productReactorClient.findAllCategoryList();

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        Mono<List<SkuInfo>> newPersonSkuInfoListMono = productReactorClient.findNewPersonSkuInfoList();

        //4 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        Mono<List<SkuEs>> hotSkuListMono = skuReactorClient.findHotSkuList();

        //TODO:若leaderAddressVoMono返回的结果为Mono.empty(),则Mono.zip整个方法不会进入，该如何处理
        //5 封装获取数据到map集合，返回
        return Mono.zip(leaderAddressVoMono,categoryListMono.switchIfEmpty(Mono.just(new ArrayList<>())),newPersonSkuInfoListMono.switchIfEmpty(Mono.just(new ArrayList<>())), hotSkuListMono.switchIfEmpty(Mono.just(new ArrayList<>()))).map(tuple -> {
            result.put("leaderAddressVo",tuple.getT1());
            result.put("categoryList", tuple.getT2());
            result.put("newPersonSkuInfoList", tuple.getT3());
            result.put("hotSkuList", tuple.getT4());
            return result;
        });
    }
}
