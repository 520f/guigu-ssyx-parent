package com.atguigu.ssyx.user.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.enums.user.User;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {

    @Autowired
    private UserService userService;

    //用户微信授权登录
    @Operation(description = "微信登录获取openid(小程序)")
    @GetMapping("/wxLogin/{code}")
    public Mono<Result<Map<String,Object>>> loginWx(@PathVariable String code) {
        //1 得到微信返回code临时票据值
        //2 拿着code + 小程序id + 小程序秘钥 请求微信接口服务
        //// 使用HttpClient工具请求
        //小程序id
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        //小程序秘钥
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        //get请求
        //拼接请求地址+参数
        /// 地址?name=value&name1=value1
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=%s" +
                "&secret=%s" +
                "&js_code=%s" +
                "&grant_type=authorization_code";
        String tokenUrl = String.format(url,
                                        wxOpenAppId,
                                        wxOpenAppSecret,
                                        code);
        //HttpClient发送get请求
        String result;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        //3 请求微信接口服务，返回两个值 session_key 和 openid
        //// openId是你微信唯一标识
        JSONObject jsonObject = JSONObject.parseObject(result);
        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");

//        openid = "odo3j4q2KskkbbW-krfE-cAxUnzU1";
        //4 添加微信用户信息到数据库里面
        //// 操作user表
        //// 判断是否是第一次使用微信授权登录：如何判断？openId
        User user = userService.getUserByOpenId(openid);
        if(user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

        //5 根据userId查询提货点和团长信息
        ////提货点  user表  user_delivery表
        ////团长    leader表
        User finalUser = user;
        return userService.getLeaderAddressByUserId(user.getId()).map(leaderAddressVo->{
            //6 使用sa-token框架根据user.id生成token字符串
            StpUtil.login(finalUser.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            String token = tokenInfo.getTokenValue();


            //7 获取当前登录用户信息，放到Redis里面，设置有效时间
            UserLoginVo userLoginVo = userService.getUserLoginVo(finalUser.getId());
            tokenInfo.setLoginDevice("WX");
            tokenInfo.setTag(JSON.toJSONString(userLoginVo));

            //8 需要数据封装到map返回
            Map<String,Object> map = new HashMap<>();
            map.put("user", finalUser);
            map.put("token",token);
            map.put("leaderAddressVo",leaderAddressVo);
            return Result.ok(map);
        });

    }

    @PostMapping("/auth/updateUser")
    @Operation(description = "更新用户昵称与头像")
    public Mono<Result<Boolean>> updateUser(@RequestBody User user) {
        Long userId = StpUtil.getLoginId(-1L);
        //获取当前登录用户id
        User user1 = userService.getById(userId);
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Mono.just(Result.ok(null));
    }
}
