package com.atguigu.ssyx.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.order.OrderInfo;
import com.atguigu.ssyx.order.service.OrderInfoService;
import com.atguigu.ssyx.vo.order.OrderConfirmVo;
import com.atguigu.ssyx.vo.order.OrderSubmitVo;
import com.atguigu.ssyx.vo.order.OrderUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@RestController
@RequestMapping(value="/api/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //订单查询
    @GetMapping("auth/findUserOrderPage/{page}/{limit}")
    public Mono<Result<IPage<OrderInfo>>> findUserOrderPage(
            @Parameter(name = "page", description = "当前页码", required = true)
            @PathVariable Long page,
            @Parameter(name = "limit", description = "每页记录数", required = true)
            @PathVariable Long limit,
            @Parameter(name = "orderVo", description = "查询对象", required = false)
            OrderUserQueryVo orderUserQueryVo) {
        //获取userId
        Long userId = StpUtil.getLoginId(-1L);
        orderUserQueryVo.setUserId(userId);

        //分页查询条件
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        return orderInfoService.getOrderInfoByUserIdPage(pageParam,orderUserQueryVo)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Mono<Result<ResultCodeEnum>> queryPayStatus(
            @Parameter(name = "orderNo", description = "订单No", required = true)
            @PathVariable("orderNo") String orderNo) {
        System.out.println(new Date());
        for (int i = 0; i <=3; i++) {
            if(i==3) {
                return Mono.just(Result.ok(ResultCodeEnum.SUCCESS));
            }
        }
        return Mono.just(Result.ok(ResultCodeEnum.URL_ENCODE_ERROR));
    }

    @Operation(description = "确认订单")
    @GetMapping("auth/confirmOrder")
    public Mono<Result<OrderConfirmVo>> confirm() {
        return orderInfoService.confirmOrder().mapNotNull(Result::ok).switchIfEmpty(Mono.just(Result.fail(null))).subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "生成订单")
    @PostMapping("auth/submitOrder")
    public Mono<Result<Long>> submitOrder(@RequestBody OrderSubmitVo orderParamVo) {
        return orderInfoService.submitOrder(orderParamVo).mapNotNull(Result::ok).switchIfEmpty(Mono.just(Result.fail(null))).subscribeOn(Schedulers.parallel());
    }

    @Operation(description = "获取订单详情")
    @GetMapping("auth/getOrderInfoById/{orderId}")
    public Mono<Result<OrderInfo>> getOrderInfoById(@PathVariable("orderId") Long orderId){
        return orderInfoService.getOrderInfoById(orderId).mapNotNull(Result::ok).switchIfEmpty(Mono.just(Result.fail(null))).subscribeOn(Schedulers.parallel());
    }

    //根据orderNo查询订单信息
    @GetMapping("inner/getOrderInfo/{orderNo}")
    public Mono<Result<OrderInfo>> getOrderInfo(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.getOrderInfoByOrderNo(orderNo).mapNotNull(Result::ok).switchIfEmpty(Mono.just(Result.fail(null))).subscribeOn(Schedulers.parallel());
    }
}

