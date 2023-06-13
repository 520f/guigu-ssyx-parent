package com.atguigu.ssyx.vo.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Leader")
public class LeaderVo implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Schema(name = "用户id")
//	@TableField("user_id")
//	private Long userId;
//
//	@Schema(name = "区域id")
//	@TableField("region_id")
//	private Long regionId;

	@Schema(name = "有无门店")
	@TableField("have_store")
	private Integer haveStore;

	@Schema(name = "提货点名称")
	@TableField("take_name")
	private String takeName;

	@Schema(name = "省")
	@TableField("province")
	private Long province;

	@Schema(name = "城市")
	@TableField("city")
	private Long city;

	@Schema(name = "区域")
	@TableField("district")
	private Long district;

	@Schema(name = "详细地址")
	@TableField("detail_address")
	private String detailAddress;

	@Schema(name = "提货点类型；1->宝妈；2->便利店店主；3->快递站点；4->物业中心")
	@TableField("take_type")
	private String takeType;

	@Schema(name = "营业时间")
	@TableField("work_time")
	private String workTime;

	@Schema(name = "名称")
	@TableField("name")
	private String name;

	@Schema(name = "手机号码")
	@TableField("phone")
	private String phone;

	@Schema(name = "身份证")
	@TableField("id_no")
	private String idNo;

	@Schema(name = "身份证图片路径")
	@TableField("id_no_url1")
	private String idNoUrl1;

	@Schema(name = "身份证图片路径")
	@TableField("id_no_url2")
	private String idNoUrl2;

	@Schema(name = "经度")
	@TableField("longitude")
	private Double longitude;

	@Schema(name = "纬度")
	@TableField("latitude")
	private Double latitude;

	@Schema(name = "门店照片")
	@TableField("store_path")
	private String storePath;

}