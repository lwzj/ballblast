package com.lsj.ballblast.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@ApiModel("游戏结束返回的数据")
public class GameOverResp {
    private Integer gameLevel;
    private Integer goldCoin;
    private Integer maxScore;
}
