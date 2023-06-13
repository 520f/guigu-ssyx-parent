package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LeaderUser")
@TableName("leader_user")
public class LeaderUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "团长ID")
	@TableField("leader_id")
	private String leaderId;

	@Schema(name = "userId")
	@TableField("user_id")
	private Long userId;

}