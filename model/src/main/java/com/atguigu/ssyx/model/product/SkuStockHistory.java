package com.atguigu.ssyx.model.product;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SkuStockHistory")
@TableName("sku_stock_history")
public class SkuStockHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "skuId")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name= "销售价格")
	@TableField("price")
	private String price;

	@Schema(name= "库存")
	@TableField("stock")
	private Integer stock;

	@Schema(name= "销量")
	@TableField("sale")
	private Integer sale;

	@Schema(name= "销售日期")
	@TableField("sale_date")
	private Date saleDate;

	@Schema(name= "仓库")
	@TableField("ware_id")
	private Long wareId;

}