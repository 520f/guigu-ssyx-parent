package com.atguigu.ssyx.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegionVo {

	@Schema(name = "开通区域")
	private Long regionId;

	@Schema(name = "区域名称")
	private String regionName;

}

