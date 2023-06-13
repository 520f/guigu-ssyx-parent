package com.atguigu.ssyx.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegionWareQueryVo {
	
	@Schema(name = "关键字")
	private String keyword;

}

