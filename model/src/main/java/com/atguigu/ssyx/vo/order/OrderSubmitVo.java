package com.atguigu.ssyx.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderSubmitVo {

	@Schema(name = "使用预生产订单号防重")
	private String orderNo;

	@Schema(name = "用户id")
	private Long userId;

	@Schema(name = "团长id")
	private Long leaderId;

	@Schema(name = "收货人姓名")
	private String receiverName;

	@Schema(name = "收货人电话")
	private String receiverPhone;

	@Schema(name = "下单选中的优惠券id")
	private Long couponId;

//	@ApiModelProperty("购买的sku信息")
//	private List<Long> skuIdList;
}

