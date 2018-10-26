package com.lsj.ballblast.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsj.ballblast.config.App;
import com.lsj.ballblast.dao.UserMapper;
import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.model.User;
import com.lsj.ballblast.service.UserService;
import com.lsj.ballblast.utils.AppUtil;
import com.lsj.ballblast.utils.HttpUtil;
import com.lsj.ballblast.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    public static final String IP_QUERY_URI = "http://ip.ws.126.net/ipquery?ip=";


    private static final String ERR_MSG = "errmsg";
    private static final int POWER_LIMIT = 20;
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
            Map<String,Object> userMap = objectMapper.readValue(wxUser, Map.class);
            String sessionKey = (String) userMap.get("session_key");
            String openId = (String) userMap.get("openid");
            if (userMap.get(ERR_MSG) != null || sessionKey == null || openId == null) {
                log.error("微信登陆出错-->userMap:{}",userMap);
            }
            //查询该用户是否存在
            User user = userMapper.findOneByOpenId(openId);
            Boolean newUser = false;
            if (user == null) {
                //数据库插入一条新数据
            }
        }
        return null;
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
