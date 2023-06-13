package com.atguigu.ssyx.model.order;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderReturnApply")
@TableName("order_return_apply")
public class OrderReturnApply extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "订单id")
	@TableField("order_id")
	private Long orderId;

	@Schema(name = "团长门店id")
	@TableField("leader_id")
	private Long leaderId;

	@Schema(name = "退货商品id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "订单编号")
	@TableField("order_sn")
	private String orderSn;

	@Schema(name = "会员用户名")
	@TableField("name")
	private String name;

	@Schema(name = "退款金额")
	@TableField("return_amount")
	private String returnAmount;

	@Schema(name = "退货人姓名")
	@TableField("return_name")
	private String returnName;

	@Schema(name = "退货人电话")
	@TableField("return_phone")
	private String returnPhone;

	@Schema(name = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
	@TableField("status")
	private Integer status;

	@Schema(name = "处理时间")
	@TableField("handle_time")
	private Date handleTime;

	@Schema(name = "商品图片")
	@TableField("sku_pic")
	private String skuPic;

	@Schema(name = "商品名称")
	@TableField("spu_name")
	private String spuName;

	@Schema(name = "退货数量")
	@TableField("sku_num")
	private Integer skuNum;

	@Schema(name = "商品单价")
	@TableField("sku_price")
	private String skuPrice;

	@Schema(name = "商品实际支付单价")
	@TableField("sku_real_price")
	private String skuRealPrice;

	@Schema(name = "原因")
	@TableField("reason")
	private String reason;

	@Schema(name = "描述")
	@TableField("description")
	private String description;

	@Schema(name = "凭证图片，以逗号隔开")
	@TableField("proof_pics")
	private String proofPics;

	@Schema(name = "处理备注")
	@TableField("handle_note")
	private String handleNote;

	@Schema(name = "处理人员")
	@TableField("handle_man")
	private String handleMan;

	@Schema(name = "收货人")
	@TableField("receive_man")
	private String receiveMan;

	@Schema(name = "收货时间")
	@TableField("receive_time")
	private Date receiveTime;

	@Schema(name = "收货备注")
	@TableField("receive_note")
	private String receiveNote;

}