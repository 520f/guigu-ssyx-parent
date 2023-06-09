//
//
package com.atguigu.ssyx.model.acl;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "角色")
@TableName("role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "角色名称")
	@TableField("role_name")
	private String roleName;

	@Schema(name = "备注")
	@TableField("remark")
	private String remark;

}

