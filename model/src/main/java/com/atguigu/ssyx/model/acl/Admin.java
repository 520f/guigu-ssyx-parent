package com.atguigu.ssyx.model.acl;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "用户")
@TableName("admin")
public class Admin extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "用户名")
	@TableField("username")
	private String username;

	@Schema(name = "密码")
	@TableField("password")
	private String password;

	@Schema(name = "昵称")
	@TableField("name")
	private String name;

	@Schema(name = "手机")
	@TableField("phone")
	private String phone;

	@Schema(name = "仓库id")
	@TableField("ware_id")
	private Long wareId;

	@Schema(name = "角色名称")
	@TableField(exist = false)
	private String roleName;
}



