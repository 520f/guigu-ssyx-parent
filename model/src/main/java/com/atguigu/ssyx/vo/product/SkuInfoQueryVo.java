package com.atguigu.ssyx.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SkuInfoQueryVo {
	
	@Schema(name = "分类id")
	private Long categoryId;

	@Schema(name = "商品类型：0->普通商品 1->秒杀商品")
	private String skuType;

	@Schema(name = "spu名称")
	private String keyword;

}

