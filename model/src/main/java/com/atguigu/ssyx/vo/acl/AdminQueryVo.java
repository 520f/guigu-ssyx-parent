//
//
package com.atguigu.ssyx.vo.acl;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 用户查询实体
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@Schema(description = "用户查询实体")
public class AdminQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name= "用户名")
	private String username;

	@Schema(name= "昵称")
	private String name;

}

