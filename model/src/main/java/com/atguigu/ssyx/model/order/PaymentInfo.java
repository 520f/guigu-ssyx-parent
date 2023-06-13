package com.atguigu.ssyx.model.order;

import java.math.BigDecimal;
import java.util.Date;

import com.atguigu.ssyx.enums.PaymentStatus;
import com.atguigu.ssyx.enums.PaymentType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "PaymentInfo")
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "对外业务编号")
	@TableField("order_no")
	private String orderNo;

	@Schema(name= "订单编号")
	@TableField("order_id")
	private Long orderId;

	@Schema(name= "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(name= "支付类型（微信 支付宝）")
	@TableField("payment_type")
	private PaymentType paymentType;

	@Schema(name= "交易编号")
	@TableField("trade_no")
	private String tradeNo;

	@Schema(name= "支付金额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@Schema(name= "交易内容")
	@TableField("subject")
	private String subject;

	@Schema(name= "支付状态")
	@TableField("payment_status")
	private PaymentStatus paymentStatus;

	@Schema(name= "回调时间")
	@TableField("callback_time")
	private Date callbackTime;

	@Schema(name= "回调信息")
	@TableField("callback_content")
	private String callbackContent;

}