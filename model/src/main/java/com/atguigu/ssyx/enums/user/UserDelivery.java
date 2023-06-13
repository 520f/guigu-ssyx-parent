package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserDelivery")
@TableName("user_delivery")
public class UserDelivery extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "会员ID")
	@TableField("user_id")
	private Long userId;

	@Schema(name = "团长id")
	@TableField("leader_id")
	private Long leaderId;

	@Schema(name = "仓库id")
	@TableField("ware_id")
	private Long wareId;

	@Schema(name = "是否默认")
	@TableField("is_default")
	private Integer isDefault;

}