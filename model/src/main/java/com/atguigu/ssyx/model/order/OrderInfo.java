package com.atguigu.ssyx.model.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atguigu.ssyx.enums.OrderStatus;
import com.atguigu.ssyx.enums.ProcessStatus;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "会员_id")
	@TableField("user_id")
	private Long userId;

	@Schema(name = "昵称")
	@TableField("nick_name")
	private String nickName;

	@Schema(name = "订单号")
	@TableField("order_no")
	private String orderNo;

	@Schema(name = "使用的优惠券")
	@TableField("coupon_id")
	private Long couponId;

	@Schema(name = "订单总额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@Schema(name = "促销活动金额")
	@TableField("activity_amount")
	private BigDecimal activityAmount;

	@Schema(name = "优惠券")
	@TableField("coupon_amount")
	private BigDecimal couponAmount;

	@Schema(name = "原价金额")
	@TableField("original_total_amount")
	private BigDecimal originalTotalAmount;

	@Schema(name = "运费")
	@TableField("feight_fee")
	private BigDecimal feightFee;

	@Schema(name = "减免运费")
	@TableField("feight_fee_reduce")
	private BigDecimal feightFeeReduce;

	@Schema(name = "可退款日期（签收后1天）")
	@TableField("refundable_time")
	private Date refundableTime;

	@Schema(name = "支付方式【1->微信】")
	@TableField("pay_type")
	private Integer payType;

	@Schema(name = "订单来源[0->小程序；1->H5]")
	@TableField("source_type")
	private Integer sourceType;

	@Schema(name = "订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】")
	@TableField("order_status")
	private OrderStatus orderStatus;

	@Schema(name = "进度状态")
	@TableField("process_status")
	private ProcessStatus processStatus;

	@Schema(name = "团长id")
	@TableField("leader_id")
	private Long leaderId;

	@Schema(name = "团长名称")
	@TableField("leader_name")
	private String leaderName;

	@Schema(name = "团长手机")
	@TableField("leader_phone")
	private String leaderPhone;

	@Schema(name = "提货点名称")
	@TableField("take_name")
	private String takeName;

	@Schema(name = "收货人姓名")
	@TableField("receiver_name")
	private String receiverName;

	@Schema(name = "收货人电话")
	@TableField("receiver_phone")
	private String receiverPhone;

	@Schema(name = "收货人邮编")
	@TableField("receiver_post_code")
	private String receiverPostCode;

	@Schema(name = "省份/直辖市")
	@TableField("receiver_province")
	private String receiverProvince;

	@Schema(name = "城市")
	@TableField("receiver_city")
	private String receiverCity;

	@Schema(name = "区")
	@TableField("receiver_district")
	private String receiverDistrict;

	@Schema(name = "详细地址")
	@TableField("receiver_address")
	private String receiverAddress;

	@Schema(name = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("payment_time")
	private Date paymentTime;

	@Schema(name = "发货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("delivery_time")
	private Date deliveryTime;

	@Schema(name = "提货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("take_time")
	private Date takeTime;

	@Schema(name = "确认收货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("receive_time")
	private Date receiveTime;

	@Schema(name = "订单备注")
	@TableField("remark")
	private String remark;

	@Schema(name = "取消订单时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("cancel_time")
	private Date cancelTime;

	@Schema(name = "取消订单原因")
	@TableField("cancel_reason")
	private String cancelReason;

	@Schema(name = "仓库id")
	@TableField("ware_id")
	private Long wareId;

	@Schema(name = "团长佣金")
	@TableField("commission_amount")
	private BigDecimal commissionAmount;

	@Schema(name = "订单项列表")
	@TableField(exist = false)
	private List<OrderItem> orderItemList;

}