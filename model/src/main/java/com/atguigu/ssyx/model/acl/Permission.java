//
//
package com.atguigu.ssyx.model.acl;

import java.util.List;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "权限")
@TableName("permission")
public class Permission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Schema(name = "所属上级")
	@TableField("pid")
	private Long pid;

	@Schema(name = "名称")
	@TableField("name")
	private String name;

	@Schema(name = "名称编码")
	@TableField("code")
	private String code;

	@Schema(name = "跳转页面code")
	@TableField("to_code")
	private String toCode;

	@Schema(name = "类型(1:菜单,2:按钮)")
	@TableField("type")
	private Integer type;

	@Schema(name = "状态(0:禁止,1:正常)")
	@TableField("status")
	private Integer status;

	@Schema(name = "层级")
	@TableField(exist = false)
	private Integer level;

	@Schema(name = "下级")
	@TableField(exist = false)
	private List<Permission> children;

	@Schema(name = "是否选中")
	@TableField(exist = false)
	private boolean isSelect;

}

