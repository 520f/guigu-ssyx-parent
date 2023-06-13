package com.atguigu.ssyx.vo.product;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WareQueryVo {
	
	@Schema(name = "名称")
	private String name;

	@Schema(name = "省code")
	private String province;

	@Schema(name = "城市code")
	private String city;

	@Schema(name = "区域code")
	private String district;

	@Schema(name = "详细地址")
	private String detailAddress;

	@Schema(name = "经度")
	private String longitude;

	@Schema(name = "纬度")
	private String latitude;

	@Schema(name = "创建时间")
	private Date createTime;

	@Schema(name = "更新时间")
	private Date updateTime;

	@Schema(name = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

