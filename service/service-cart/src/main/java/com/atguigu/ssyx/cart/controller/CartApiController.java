package com.atguigu.ssyx.cart.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.atguigu.ssyx.activity.client.ActivityReactorClient;
import com.atguigu.ssyx.cart.service.CartInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.order.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartInfoService cartInfoService;
    
    @Autowired
    private ActivityReactorClient activityReactorClient;

    //1 根据skuId选中
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result<String> checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked) {
        //获取userId
        Long userId = StpUtil.getLoginId(-1L);
        //调用方法
        cartInfoService.checkCart(userId,skuId,isChecked);
        return Result.ok(null);
    }

    //2 全选
    @GetMapping("checkAllCart/{isChecked}")
    public Result<String> checkAllCart(@PathVariable Integer isChecked) {
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.checkAllCart(userId,isChecked);
        return Result.ok(null);
    }

    //3 批量选中
    @PostMapping("batchCheckCart/{isChecked}")
    public Result<String> batchCheckCart(@RequestBody List<Long> skuIdList,
                                 @PathVariable Integer isChecked) {
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.batchCheckCart(skuIdList,userId,isChecked);
        return Result.ok(null);
    }

    /**
     * 查询带优惠卷的购物车
     *
     */
    @GetMapping("activityCartList")
    public Mono<Result<?>> activityCartList() {
        // 获取用户Id
        Long userId = StpUtil.getLoginId(-1L);
        return cartInfoService.getCartList(userId).flatMap(cartInfoList -> {
            if (CollectionUtils.isEmpty(cartInfoList)) {
                return Mono.just(Result.ok(null));
            }
            return activityReactorClient.findCartActivityAndCoupon(cartInfoList, userId)
                    .map(Result::ok)
                    .switchIfEmpty(Mono.just(Result.ok(null)));
        }).subscribeOn(Schedulers.parallel());

    }

    //购物车列表
    @GetMapping("cartList")
    public Mono<Result<List<CartInfo>>> cartList() {
        //获取userId
        Long userId = StpUtil.getLoginId(-1L);
        return cartInfoService.getCartList(userId)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    //添加商品到购物车
    //添加内容：当前登录用户id，skuId，商品数量
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Mono<Result<String>> addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        //获取当前登录用户Id
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.addToCart(userId,skuId,skuNum);
        return Mono.just(Result.ok(null));
    }

    //根据skuId删除购物车
    @DeleteMapping("deleteCart/{skuId}")
    public Mono<Result<Boolean>> deleteCart(@PathVariable("skuId") Long skuId) {
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.deleteCart(skuId,userId);
        return Mono.just(Result.ok(null));
    }

    //清空购物车
    @DeleteMapping("deleteAllCart")
    public Mono<Result<Boolean>> deleteAllCart() {
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.deleteAllCart(userId);
        return Mono.just(Result.ok(null));
    }

    //批量删除购物车 多个skuId
    @DeleteMapping("batchDeleteCart")
    public Mono<Result<Boolean>> batchDeleteCart(@RequestBody List<Long> skuIdList) {
        Long userId = StpUtil.getLoginId(-1L);
        cartInfoService.batchDeleteCart(skuIdList,userId);
        return Mono.just(Result.ok(null));
    }

    /**
     * 根据用户Id 查询购物车列表
     *
     */
    @GetMapping("inner/getCartCheckedList/{userId}")
    public Mono<List<CartInfo>> getCartCheckedList(@PathVariable("userId") Long userId) {
        return cartInfoService.getCartCheckedList(userId)
                .subscribeOn(Schedulers.parallel());
    }
}
