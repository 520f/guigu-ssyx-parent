package com.atguigu.ssyx.model.order;

import java.math.BigDecimal;
import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CartInfo")
public class CartInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "用户id")
	private Long userId;

	@Schema(name = "分类id")
	private Long categoryId;

	@Schema(name = "商品类型：0->普通商品 1->秒杀商品")
	private Integer skuType;

	@Schema(name = "是否新人专享：0->否；1->是")
	private Integer isNewPerson;

	@Schema(name = "sku名称 (冗余)")
	private String skuName;

	@Schema(name = "skuid")
	private Long skuId;

	@Schema(name = "放入购物车时价格")
	private BigDecimal cartPrice;

	@Schema(name = "数量")
	private Integer skuNum;

	@Schema(name = "限购个数")
	private Integer perLimit;

	@Schema(name = "图片文件")
	private String imgUrl;

	@Schema(name = "isChecked")
	private Integer isChecked;

	@Schema(name = "状态")
	private Integer status;

	@Schema(name = "秒杀开始时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date startTime;

	@Schema(name = "秒杀结束时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;

	@Schema(name = "仓库")
	private Long wareId;

	@Schema(name = "当天已购买个数")
	private Integer currentBuyNum = 0;

}