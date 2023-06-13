package com.atguigu.ssyx.cart.controller;

import com.atguigu.ssyx.activity.client.ActivityFeignClient;
import com.atguigu.ssyx.cart.service.CartInfoService;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartInfoService cartInfoService;
    
    @Autowired
    private ActivityFeignClient activityFeignClient;

    //1 根据skuId选中
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result<String> checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked) {
        //获取userId
        Long userId = AuthContextHolder.getUserId();
        //调用方法
        cartInfoService.checkCart(userId,skuId,isChecked);
        return Result.ok(null);
    }

    //2 全选
    @GetMapping("checkAllCart/{isChecked}")
    public Result<String> checkAllCart(@PathVariable Integer isChecked) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.checkAllCart(userId,isChecked);
        return Result.ok(null);
    }

    //3 批量选中
    @PostMapping("batchCheckCart/{isChecked}")
    public Result<String> batchCheckCart(@RequestBody List<Long> skuIdList,
                                 @PathVariable Integer isChecked) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchCheckCart(skuIdList,userId,isChecked);
        return Result.ok(null);
    }

    /**
     * 查询带优惠卷的购物车
     *
     */
    @GetMapping("activityCartList")
    public Result<OrderConfirmVo> activityCartList() {
        // 获取用户Id
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        return Result.ok(activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId));
    }

    //购物车列表
    @GetMapping("cartList")
    public Result<List<CartInfo>> cartList() {
        //获取userId
        Long userId = AuthContextHolder.getUserId();
        return Result.ok(cartInfoService.getCartList(userId));
    }

    //添加商品到购物车
    //添加内容：当前登录用户id，skuId，商品数量
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result<String> addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        //获取当前登录用户Id
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.addToCart(userId,skuId,skuNum);
        return Result.ok(null);
    }

    //根据skuId删除购物车
    @DeleteMapping("deleteCart/{skuId}")
    public Result<Boolean> deleteCart(@PathVariable("skuId") Long skuId) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteCart(skuId,userId);
        return Result.ok(null);
    }

    //清空购物车
    @DeleteMapping("deleteAllCart")
    public Result<Boolean> deleteAllCart() {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteAllCart(userId);
        return Result.ok(null);
    }

    //批量删除购物车 多个skuId
    @DeleteMapping("batchDeleteCart")
    public Result<Boolean> batchDeleteCart(@RequestBody List<Long> skuIdList) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchDeleteCart(skuIdList,userId);
        return Result.ok(null);
    }

    /**
     * 根据用户Id 查询购物车列表
     *
     */
    @GetMapping("inner/getCartCheckedList/{userId}")
    public List<CartInfo> getCartCheckedList(@PathVariable("userId") Long userId) {
        return cartInfoService.getCartCheckedList(userId);
    }
}
