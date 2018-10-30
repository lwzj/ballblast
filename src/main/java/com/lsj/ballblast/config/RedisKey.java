package com.lsj.ballblast.config;

/**
 * @author Y.S.K
 */
public class RedisKey {

    /**
     * 玩家的最高分
     */
    public static final String MAX_SCORE = "max_score_";

    /**
     * token相关
     */
    public static final String TOKEN_PREFIX = "token_";
    public static final String TOKEN_UID_PREFIX = "token_uid_";
    /**
     * 活跃用户集合
     */
    public static final String ACTIVE_USERS = "active_users";
    /**
     * 游戏活跃的某个用户
     */
    public static final String GAME_ACTIVE_USER_PREFIX = "game_active_user_";
    /**
     * 在线的某个用户
     */
//    public static final String ONLINE_USER_PREFIX = "online_user_";

    /**
     * 引导前缀
     */
    public static final String GUIDE_PREFIX = "guide_";

    /**
     * 分享图前缀
     */
    public static final String SHARE_IMG_PREFIX = "share_img_";

    /**
     * 游戏启动次数
     */
    public static final String GAME_LAUNCH_TIMES_PREFIX = "game_launch_times_";

    /**
     * 过关信息前缀
     */
    public static final String PASS_LEVEL_PREFIX = "pass_level_";

    public static final String NO_PASS_LEVEL_PREFIX = "no_pass_level_";

    /**
     * 分享限制
     */
    public static final String SHARE_LIMIT_ = "share_limit_";

    /**
     * 用户sessionKey
     */
    public static final String SESSION_KEY_PREFIX="session_key_";

    /**
     * 用户心跳检测
     */
    public static final String HEART_CHECK_PREFIX="heart_check_";

    /**
     * 关卡信息前缀
     */
    public static final String LEVEL_INFO_PREFIX="level_info_";
    /**
     * 频繁分享限制
     */
    public static final String QUICK_SHARE_LIMIT="quick_share_";

    public static final String PET_DATA_PREFIX="pet_data_";


}
