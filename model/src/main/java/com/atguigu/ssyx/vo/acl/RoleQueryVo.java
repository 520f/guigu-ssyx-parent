//
//
package com.atguigu.ssyx.vo.acl;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 角色查询实体
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "角色查询实体")
public class RoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "角色名称")
	private String roleName;

}

