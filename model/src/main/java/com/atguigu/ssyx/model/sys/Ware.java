package com.atguigu.ssyx.model.sys;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ware")
@TableName("ware")
public class Ware extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "名称")
	@TableField("name")
	private String name;

	@Schema(name = "省code")
	@TableField("province")
	private String province;

	@Schema(name = "城市code")
	@TableField("city")
	private String city;

	@Schema(name = "区域code")
	@TableField("district")
	private String district;

	@Schema(name = "详细地址")
	@TableField("detail_address")
	private String detailAddress;

	@Schema(name = "经度")
	@TableField("longitude")
	private String longitude;

	@Schema(name = "纬度")
	@TableField("latitude")
	private String latitude;

}