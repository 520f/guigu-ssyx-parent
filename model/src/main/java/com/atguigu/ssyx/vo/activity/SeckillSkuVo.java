package com.atguigu.ssyx.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "秒杀商品信息")
public class SeckillSkuVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "秒杀商品主键id")
	private Long seckillSkuId;

	@Schema(name = "skuId")
	private Long skuId;

	@Schema(name = "spu名称")
	private String skuName;

	@Schema(name = "展示图片")
	private String imgUrl;

	@Schema(name = "秒杀价格")
	private BigDecimal seckillPrice;

	@Schema(name = "秒杀总量")
	private Integer seckillStock;

	@Schema(name = "每人限购数量")
	private Integer seckillLimit;

	@Schema(name = "秒杀销量")
	private Integer seckillSale;

	@Schema(name = "场次名称")
	private String timeName;

	@Schema(name = "每日开始时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date startTime;

	@Schema(name = "每日结束时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;

	@Schema(name = "场次状态 1：已开抢 2：抢购中 3：即将开抢")
	@TableField(exist = false)
	private Integer timeStaus;

}