package com.lsj.ballblast.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 离线奖励
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeGold {
    private Integer id;
    private Integer level;//坦克等级
    private Integer goldCoinCost;//升级所需的金钱数
    private Integer speed;
    private Integer power;
    private Integer goldCoin;
    private Integer offlineIncome;
}
