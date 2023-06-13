package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserLoginLog")
@TableName("user_login_log")
public class UserLoginLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(name = "登录ip")
	@TableField("ip")
	private String ip;

	@Schema(name = "登录城市")
	@TableField("city")
	private String city;

	@Schema(name = "登录类型【0-web，1-移动】")
	@TableField("type")
	private Boolean type;

}