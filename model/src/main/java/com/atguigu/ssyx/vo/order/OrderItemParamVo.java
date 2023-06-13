package com.atguigu.ssyx.vo.order;

import java.io.Serializable;

import com.atguigu.ssyx.enums.SkuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderItem")
public class OrderItemParamVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "商品类型：0->普通商品 1->秒杀商品")
	private SkuType skuType;

	@Schema(name = "商品sku编号")
	private Long skuId;

	@Schema(name = "商品购买的数量")
	private Integer skuNum;

}