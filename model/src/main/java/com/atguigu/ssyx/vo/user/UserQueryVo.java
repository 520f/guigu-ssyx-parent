package com.atguigu.ssyx.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserQueryVo {
	
	@Schema(name = "昵称")
	private String nickName;

	@Schema(name = "身份证号码")
	private String idNo;

	@Schema(name = "性别")
	private String sex;

	@Schema(name = "电话号码")
	private String phone;

}

