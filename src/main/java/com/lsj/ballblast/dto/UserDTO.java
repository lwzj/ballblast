package com.lsj.ballblast.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@ApiModel("登录接口返回的用户对象")
public class UserDTO {
    /**
     * 用户token
     */
    private String token;
    /**
     * uid
     */
    private Integer uid;
    /**
     * 游戏关卡
     */
    private Integer gameLevel;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 玩家头像
     */
    private String avatar;
    /**
     * 是否是新用户
     */
    private Boolean isNew;
    /**
     * 离线收益
     */
    private Integer offLineIncome;

    /**
     * 火力
     */
    private Integer power;

    /**
     * 射速
     */
    private Integer speed;
    /**
     * 升级需要的金币
     */
    private Integer goldCoinCost;

    /**
     * 金币加成等级
     */
    private Integer goldAdd;
    /**
     * 离线收益等级
     */
    private Integer offLineL;
  /**
     * 玩家的金币数
     */
    private Integer goldCoin;

}
