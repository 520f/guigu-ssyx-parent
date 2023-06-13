package com.atguigu.ssyx.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SeckillQueryVo {
	
	@Schema(name = "活动标题")
	private String title;

	@Schema(name = "上下线状态")
	private Integer status;


}

