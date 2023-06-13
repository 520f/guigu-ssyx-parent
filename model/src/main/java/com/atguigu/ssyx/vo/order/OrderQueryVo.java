package com.atguigu.ssyx.vo.order;

import com.atguigu.ssyx.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderQueryVo {
	

	@Schema(name = "订单号")
	private String orderNo;

	@Schema(name = "收货人信息")
	private String receiver;

	@Schema(name = "订单状态")
	private OrderStatus orderStatus;

	@Schema(name = "团长id")
	private Long leaderId;

	@Schema(name = "仓库id")
	private Long wareId;

	@Schema(name = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

}

