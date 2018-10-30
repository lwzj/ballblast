package com.lsj.ballblast.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@ApiModel("升级返回的数据")
public class UpgradeResp {
    private Integer goldCoin;//玩家剩余金币数
    private Integer power ;//玩家当前火力等级
    private Integer speed;//玩家火力值
    private Integer goldCoinCost;//升级所需要的金币数
    private Integer goldAdd;//掉落金币等级
    private Integer offlineL;//离线金币等级
}
