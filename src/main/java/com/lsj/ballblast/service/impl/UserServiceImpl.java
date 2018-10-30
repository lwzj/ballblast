package com.lsj.ballblast.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsj.ballblast.annotation.TokenService;
import com.lsj.ballblast.config.App;
import com.lsj.ballblast.dao.ScoreRecordMapper;
import com.lsj.ballblast.dao.UpGradeGoldMapper;
import com.lsj.ballblast.dao.UserMapper;
import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.dto.UserDTO;
import com.lsj.ballblast.enums.ResultEnum;
import com.lsj.ballblast.model.ScoreRecord;
import com.lsj.ballblast.model.TankInfo;
import com.lsj.ballblast.model.UpgradeGold;
import com.lsj.ballblast.model.User;
import com.lsj.ballblast.service.ScoreRecordService;
import com.lsj.ballblast.service.TankInfoService;
import com.lsj.ballblast.service.UserService;
import com.lsj.ballblast.utils.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UpGradeGoldMapper upGradeGoldMapper;
    @Autowired
    private ScoreRecordMapper recordMapper;
    @Autowired
    private TankInfoService tankInfoService;
    @Autowired
    private TokenService tokenService;
    public static final String IP_QUERY_URI = "http://ip.ws.126.net/ipquery?ip=";


    private static final String ERR_MSG = "errmsg";
    private static final int BASIC_REWARD_TIME = 5; //基础奖励时间分钟
    private static final int BASIC_REWARD = 25; //基础奖励金币
    private static final int LEVEL1 = 2; //时间奖励的第一阶段 单位小时
    private static final int LEVEL2 = 12; //时间奖励的第二阶段


    private static final String A = "A";
    private static final String B = "B";
    private static final String IS_SH = "1";

    @Override
    public Result login(String code, String encryptedData, String iv, String channel, String ip, String shareImg, Integer shareId, String version) throws IOException {
        if (shareId != null) {
            channel = "分享";
        } else {
            channel = AppUtil.getChannel(channel);
        }
        String wxUser = HttpUtil.get(App.LOGIN_API, loginMap(code));
        if (StringUtil.isNullOrEmpty(wxUser)) {
            Map<String, Object> userMap = objectMapper.readValue(wxUser, Map.class);
            String sessionKey = (String) userMap.get("session_key");
            String openId = (String) userMap.get("openid");
            if (userMap.get(ERR_MSG) != null || sessionKey == null || openId == null) {
                log.error("微信登陆出错-->userMap:{}", userMap);
            }
            //查询该用户是否存在
            User user = userMapper.findOneByOpenId(openId);
            Boolean newUser = false;
            if (user == null) {
                //解密数据
                String userData = AESUtil.aesDecryptByBytes(encryptedData.replaceAll(StringUtil.BLANK_TEXT, StringUtil.PLUS),
                        sessionKey.replaceAll(StringUtil.BLANK_TEXT, StringUtil.PLUS),
                        iv.replaceAll(StringUtil.BLANK_TEXT, StringUtil.PLUS));
                if (StringUtil.isNullOrEmpty(userData)) {
                    log.error("用户数据解密出错，encryptedData{},sessionKey{},iv{}", encryptedData, sessionKey, iv);
                    return ResultUtil.error(ResultEnum.ENCRYPT_ERR);
                }
                userMap.putAll(objectMapper.readValue(userData, Map.class));
                //数据库插入一条新数据
                user = buildUser(userMap, channel, ip, shareImg, shareId);
                userMapper.insert(user);
                //初始化一些用户信息
                //初始化用户的坦克信息
                tankInfoService.insret(user.getUid());
                newUser = true;
            }
            //生成用户token
            String token = tokenService.generateToken(user.getUid());
            TankInfo tankInfo = tankInfoService.findByUid(user.getUid());
            //读取对应等级的相关属性配置，放在前段速度更快
            UpgradeGold upGradeGold = upGradeGoldMapper.findGoldByLevel(tankInfo.getLevel());
            //计算是否可以领取离线奖励
            Integer offLineIncome = 0;
            if (!newUser) {
                offLineIncome = getOffLineIncome(user.getUid());
            }

            //返回用户信息
            return ResultUtil.success(UserDTO.builder()
                    .uid(user.getUid())
                    .token(token)
                    .avatar(user.getAvatar())
                    .gameLevel(tankInfo.getGameLevel())
                    .isNew(newUser)
                    .offLineIncome(offLineIncome)
                    .nickName(user.getNickname())
                    .power(upGradeGold.getPower())
                    .speed(upGradeGold.getSpeed())
                    .offLineL(upGradeGold.getOfflineIncome())
                    .goldAdd(upGradeGold.getGoldCoin())
                    .goldCoin(tankInfo.getGoldCoin())
                    .build());
        }
        log.error("wxUser :{}", wxUser);
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public int getGold(Integer uid, Integer gold) {
        TankInfo tankInfo = tankInfoService.findByUid(uid);
        int g = tankInfo.getGoldCoin() + gold;
        tankInfoService.addGold(uid, g);
        return g;
    }

    //计算离线奖励
    private Integer getOffLineIncome(Integer uid) {
        ScoreRecord record =  recordMapper.findByUid(uid);
        if(record==null){
            return 0;
        }
        LocalDateTime endtime = record.getEndtime();
        LocalDateTime now = LocalDateTime.now();
        long l = endtime.toEpochSecond(ZoneOffset.of("+8"));//最后一次游戏记录的秒数
        long n = now.toEpochSecond(ZoneOffset.of("+8"));//当前时间的秒数
        long offlineTime = n - l;//离线秒数
        Integer gold = 0;
        if(offlineTime> 0 &&offlineTime<=BASIC_REWARD_TIME*60){
            return BASIC_REWARD;
        }else{
            if(offlineTime<=LEVEL1*60*60){
                int goldcion = (int)(offlineTime / 12);
                gold = goldcion + BASIC_REWARD;
            } else if (offlineTime > LEVEL1 * 60 * 60 && offlineTime < LEVEL2 * 60 * 60) {
                int g1 = LEVEL1 * 60 * 60 / 12;
                int g2 = (int)(offlineTime - LEVEL1*60*60)/60;
                gold = BASIC_REWARD+g1+g2;
            }else{
                int g1 = LEVEL1 * 60 * 60 / 12;
                int g2 = (LEVEL2 - LEVEL1)*60;
                gold = BASIC_REWARD+g1+g2;
            }
        }
        return gold;
    }


    private User buildUser(Map<String, Object> userMap, String channel, String ip, String shareImg, Integer shareId) {
        return User.builder()
                .avatar(String.valueOf(userMap.get("avatarUrl")))
                .channel(channel)
                .gender((Integer) userMap.get("gender"))
                .inviteCode(StringUtil.getRandomString(8))
                .ip(ip)
                .nickname((String) userMap.get("nickName"))
                .openId((String) userMap.get("openid"))
                .unionId((String) userMap.get("unionId"))
                .createTime(LocalDateTime.now())
                .city((String) userMap.get("city"))
                .province((String) userMap.get("province"))
                .country((String) userMap.get("country"))
                .version(App.getVersion())
                .shareImg(shareImg)
                .shareId(shareId)
                .build();
    }

    private Map<String, String> loginMap(String code) {
        Map<String, String> loginMap = new HashMap<>(4);
        loginMap.put("appid", App.APP_ID);
        loginMap.put("secret", App.APP_SECRET);
        loginMap.put("js_code", code);
        loginMap.put("grant_type", "authorization_code");
        return loginMap;
    }
}
