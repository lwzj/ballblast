package com.lsj.ballblast.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/9/8 16:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShare {
    private Integer uid;
    private LocalDateTime shareTime;
    private Boolean success;
    private String type;
    private String version;
    private String shareConfig;
}
