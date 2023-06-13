package com.atguigu.ssyx.vo.order;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StockStatisticsVo implements Serializable {

    @Schema(name = "skuId")
    private Long skuId;

    @Schema(name = "销售价格")
    private String price;

    @Schema(name = "销量")
    @TableField("sale")
    private Integer sale;
}
