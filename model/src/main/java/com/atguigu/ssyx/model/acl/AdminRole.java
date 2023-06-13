package com.atguigu.ssyx.model.acl;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "用户角色")
@TableName("admin_role")
public class AdminRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name= "角色id")
	@TableField("role_id")
	private Long roleId;

	@Schema(name= "用户id")
	@TableField("admin_id")
	private Long adminId;

}

