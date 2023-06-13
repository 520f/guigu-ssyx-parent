package com.atguigu.ssyx.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SeckillSkuQueryVo {
	
	@Schema(name = "秒杀活动id")
	private Long seckillId;

	@Schema(name = "活动场次id")
	private Long seckillTimeId;

}

