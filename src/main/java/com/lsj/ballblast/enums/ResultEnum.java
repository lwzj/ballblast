package com.lsj.ballblast.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/8 14:33
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultEnum {
    TEST(001, "test"),

    SUCCESS(200, "ok"),

    SERVER_ERROR(500, "服务器错误"),

    CODE_ERROR(10001, "微信code不正确"),

    NOT_LOGIN(10002, "用户未登录"),

    ENCRYPT_ERR(10003, "用户数据解密出错"),

    GET_TOKEN_ERR(10004, "获取token和openid出错"),

    NO_SUPPORT_GAME_TYPE(10005, "不支持的游戏类型"),

    PARAM_ERR(10006, "参数错误"),

    REWARD_LIMIT(10007, "蓄力奖励发放失败"),

    LOSE_SHARE_LIMIT(10008, "失败分享次数已达上限"),

    SHARE_LIMIT(10009, "体力分享次数已达上限"),

    SHARE_GROUP_LIMIT(10010, "短时间内，不建议分享到相同群呦"),

    NOT_GET_GONG_ZHONG_HAO_GIFT(10011, "该礼包已经领取过"),

    LACK_OF_GOLDCOIN(10012, "金币不足");
    private Integer state;
    private String msg;

}
