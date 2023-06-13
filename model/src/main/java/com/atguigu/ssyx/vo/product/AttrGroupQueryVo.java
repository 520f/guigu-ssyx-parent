package com.atguigu.ssyx.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AttrGroupQueryVo {

	@Schema(name = "组名")
	private String name;

}

