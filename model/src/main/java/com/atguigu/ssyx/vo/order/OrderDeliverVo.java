package com.atguigu.ssyx.vo.order;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderDeliver")
public class OrderDeliverVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "配送日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliverDate;

	@Schema(name = "团长id")
	private Long leaderId;

	@Schema(name = "司机id")
	private Long driverId;

	@Schema(name = "司机名称")
	private String driverName;

	@Schema(name = "司机电话")
	private String driverPhone;

	@Schema(name = "状态（0：默认，1：已发货，2：团长收货）")
	private Integer status;

}