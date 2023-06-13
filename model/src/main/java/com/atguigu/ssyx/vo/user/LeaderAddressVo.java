package com.atguigu.ssyx.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * UserAddress
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "用户地址")
public class LeaderAddressVo {

	@Schema(name = "用户id")
	private Long userId;

	@Schema(name = "团长id")
	private Long leaderId;

	@Schema(name = "团长名称")
	private String leaderName;

	@Schema(name = "团长电话")
	private String leaderPhone;

	@Schema(name = "仓库id")
	private Long wareId;

	@Schema(name = "提货点名称")
	private String takeName;

	@Schema(name = "省")
	private String province;

	@Schema(name = "城市")
	private String city;

	@Schema(name = "区域")
	private String district;

	@Schema(name = "详细地址")
	private String detailAddress;

	@Schema(name = "经度")
	private String longitude;

	@Schema(name = "纬度")
	private String latitude;

	@Schema(name = "门店照片")
	private String storePath;

}

