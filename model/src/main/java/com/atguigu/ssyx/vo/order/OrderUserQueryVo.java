package com.atguigu.ssyx.vo.order;

import com.atguigu.ssyx.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderUserQueryVo {

	private Long userId;

	@Schema(name = "订单状态")
	private OrderStatus orderStatus;

}

