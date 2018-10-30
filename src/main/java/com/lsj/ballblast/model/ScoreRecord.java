package com.lsj.ballblast.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 玩家每一局的分数信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRecord {
    private Integer id; //
    private Integer uid; //玩家id
    private Integer gameLevel;//关卡数
    private Integer score;//本次得分
    private Integer time;//用时
    private LocalDateTime endtime;//记录打完的时间

}
