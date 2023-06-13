package com.atguigu.ssyx.vo.activity;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SeckillSkuNoticeVo {
	
	@Schema(name = "member_id")
	private Long memberId;

	@Schema(name = "sku_id")
	private Long skuId;

	@Schema(name = "活动场次id")
	private Long sessionId;

	@Schema(name = "订阅时间")
	private Date subcribeTime;

	@Schema(name = "发送时间")
	private Date sendTime;

	@Schema(name = "通知方式[0-短信，1-邮件]")
	private Boolean noticeType;

	@Schema(name = "创建时间")
	private Date createTime;

	@Schema(name = "更新时间")
	private Date updateTime;

	@Schema(name = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

