package com.atguigu.ssyx.vo.activity;

import java.io.Serializable;
import java.util.List;

import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "活动规则")
public class ActivityRuleVo implements Serializable {
   
   private static final long serialVersionUID = 1L;

   @Schema(name = "活动id")
   private Long activityId;
   
   @Schema(name = "活动规则list")
   private List<ActivityRule> activityRuleList;

   @Schema(name = "活动参与商品list")
   private List<ActivitySku> activitySkuList;

   @Schema(name = "优惠券id列表")
   private List<Long> couponIdList;

}