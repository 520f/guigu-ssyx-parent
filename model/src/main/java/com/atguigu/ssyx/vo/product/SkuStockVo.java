package com.atguigu.ssyx.vo.product;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SkuStockVo implements Serializable {

	@Schema(name = "skuId")
	private Long skuId;

	@Schema(name = "sku类型")
	private Integer skuType;

	@Schema(name = "更新的库存数量")
	private Integer stockNum;

}

