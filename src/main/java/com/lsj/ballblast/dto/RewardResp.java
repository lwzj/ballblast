package com.lsj.ballblast.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@ApiModel("领取离线奖励返回的数据")
public class RewardResp {
    private Integer goldCoin;//金币数
}
