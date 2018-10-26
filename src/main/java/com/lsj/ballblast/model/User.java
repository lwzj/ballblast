package com.lsj.ballblast.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lw
 * @data 20188/10/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer uid;
    private String openId;//
    private String unionId;//
    private String nickname;//用户昵称
    private String avatar;//用户头像url
    private String gender;//用户性别
    private LocalDateTime createTime;//创建时间
    private LocalDateTime activeTime;//活跃时间
    private String inviteCode;//邀请码
    @Builder.Default
    private String channel="default";//用户渠道
    private String ip;//第一次登陆的ip地址
    private String city;//城市
    private String province;//省份
    private String country;//国家
    private String version;//用户注册版本
    private String shareImg;//用户是通过哪个分享图进来的
    private Integer shareId;//被哪个用户邀请进来的
}
