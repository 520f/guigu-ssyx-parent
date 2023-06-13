package com.atguigu.ssyx.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author qy
 */
@Data
public class OrderMqVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(name = "订单号")
	private String orderNo;

	@Schema(name = "团长id")
	private Long leaderId;

	@Schema(name = "团长佣金")
	private BigDecimal commissionAmount;

	@Schema(name = "订单项列表")
	private List<OrderItemMqVo> orderItemMqVoList;


}

