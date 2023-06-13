package com.atguigu.ssyx.vo.acl;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员登录信息")
public class AdminLoginVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "管理员id")
	private Long adminId;

	@Schema(name = "姓名")
	private String name;

	@Schema(name = "仓库id")
	private Long wareId;

}