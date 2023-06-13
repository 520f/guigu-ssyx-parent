package com.atguigu.ssyx.vo.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录信息")
public class UserLoginVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "用户id")
	private Long userId;

	@Schema(name = "会员头像")
	private String photoUrl;

	@Schema(name = "昵称")
	private String nickName;

	@Schema(name = "小程序open id")
	private String openId;

	@Schema(name = "是否新用户")
	private Integer isNew;

	@Schema(name = "当前登录用户团长id")
	private Long leaderId;

	@Schema(name = "仓库id")
	private Long wareId;

}