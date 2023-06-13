package com.atguigu.ssyx.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LeaderQueryVo {

	@Schema(name = "审核状态")
	private Integer checkStatus;

	@Schema(name = "关键字")
	private String keyword;

	@Schema(name = "省")
	private String province;

	@Schema(name = "城市")
	private String city;

	@Schema(name = "区域")
	private String district;

}

