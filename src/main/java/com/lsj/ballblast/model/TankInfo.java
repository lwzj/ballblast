package com.lsj.ballblast.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述
 *  玩家坦克的额信息
 * @author Y.S.K
 * @date 2018/9/1 16:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TankInfo {
    private Integer id;
    private Integer uid;
    @Builder.Default
    private Integer goldCoin = 0;//金币数
    @Builder.Default
    private Integer level = 1;//坦克等级
    @Builder.Default
    private Integer gameLevel=1;//用户在第几关
}
