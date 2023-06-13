package com.atguigu.ssyx.vo.product;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SkuStockLockVo implements Serializable {

	@Schema(name = "skuId")
	private Long skuId;

	@Schema(name = "sku个数")
	private Integer skuNum;

	@Schema(name = "是否锁定")
	private Boolean isLock = false;
}

