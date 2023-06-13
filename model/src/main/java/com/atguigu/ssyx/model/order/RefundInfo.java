package com.atguigu.ssyx.model.order;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RefundInfo")
@TableName("refund_info")
public class RefundInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "对外业务编号")
	@TableField("out_trade_no")
	private String outTradeNo;

	@Schema(name = "订单编号")
	@TableField("order_id")
	private Long orderId;

	@Schema(name = "skuId")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "支付类型（微信 支付宝）")
	@TableField("payment_type")
	private String paymentType;

	@Schema(name = "交易编号")
	@TableField("trade_no")
	private String tradeNo;

	@Schema(name = "退款金额")
	@TableField("total_amount")
	private String totalAmount;

	@Schema(name = "交易内容")
	@TableField("subject")
	private String subject;

	@Schema(name = "退款状态")
	@TableField("refund_status")
	private String refundStatus;

	@Schema(name = "回调时间")
	@TableField("callback_time")
	private Date callbackTime;

	@Schema(name = "回调信息")
	@TableField("callback_content")
	private String callbackContent;

}