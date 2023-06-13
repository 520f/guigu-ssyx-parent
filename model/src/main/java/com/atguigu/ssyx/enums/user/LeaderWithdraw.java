package com.atguigu.ssyx.enums.user;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LeaderWithdraw")
@TableName("leader_withdraw")
public class LeaderWithdraw extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "团长ID")
	@TableField("leader_id")
	private String leaderId;

	@Schema(name = "提现金额")
	@TableField("merchant_amount")
	private String merchantAmount;

	@Schema(name = "提现状态")
	@TableField("merchant_status")
	private String merchantStatus;

	@Schema(name = "提现失败原因")
	@TableField("fail_reason")
	private String failReason;

	@Schema(name = "提现支付方式")
	@TableField("payment_method")
	private String paymentMethod;

	@Schema(name = "银行名称")
	@TableField("bank_name")
	private String bankName;

	@Schema(name = "银行账号")
	@TableField("bank_account_no")
	private String bankAccountNo;

	@Schema(name = "银行账户名")
	@TableField("bank_account_name")
	private String bankAccountName;

	@Schema(name = "微信ID")
	@TableField("wechat_id")
	private String wechatId;

	@Schema(name = "提现时间")
	@TableField("withdraw_time")
	private Date withdrawTime;

	@Schema(name = "审核时间")
	@TableField("verify_time")
	private Date verifyTime;

	@Schema(name = "打款时间")
	@TableField("transfer_time")
	private Date transferTime;

	@Schema(name = "提现交易编号")
	@TableField("withdraw_no")
	private String withdrawNo;

	@Schema(name = "审核拒绝理由")
	@TableField("reject_reason")
	private String rejectReason;

	@Schema(name = "提现成功时间")
	@TableField("complete_time")
	private Date completeTime;

	@Schema(name = "提现金额")
	@TableField("payment_amount")
	private String paymentAmount;

	@Schema(name = "手续费")
	@TableField("tax_amount")
	private String taxAmount;

	@Schema(name = "备注")
	@TableField("memo")
	private String memo;

}