package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LeaderBank")
@TableName("leader_bank")
public class LeaderBank extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "团长ID")
	@TableField("leader_id")
	private String leaderId;

	@Schema(name= "账户类型(微信,银行)")
	@TableField("account_type")
	private String accountType;

	@Schema(name= "银行名称")
	@TableField("bank_name")
	private String bankName;

	@Schema(name= "银行账号")
	@TableField("bank_account_no")
	private String bankAccountNo;

	@Schema(name= "银行账户名")
	@TableField("bank_account_name")
	private String bankAccountName;

	@Schema(name= "微信ID")
	@TableField("wechat_id")
	private String wechatId;

}