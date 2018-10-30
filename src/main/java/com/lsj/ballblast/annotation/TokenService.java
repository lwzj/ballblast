package com.lsj.ballblast.annotation;

import com.lsj.ballblast.config.RedisKey;
import com.lsj.ballblast.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lei on 24/08/2017.
 * Yes, you can.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class TokenService {
    public final int INVALID_UID = -1;
    @Autowired
    private RedisTemplate<String, String> stringRedis;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> stringIntegerValueOperations;


    public String generateToken(int uid) {
        String token = UUID.randomUUID().toString();
        String redisKey = RedisKey.TOKEN_PREFIX + token;
        String tokenUidKey = RedisKey.TOKEN_UID_PREFIX + uid;

        //将key-value交叉存储，方便单点登录，用户在其他地方或者重新登录，就会删除之前的登录信息
//        stringRedis.opsForValue().set(redisKey, Integer.toString(uid), 1, TimeUnit.HOURS);
        stringIntegerValueOperations.set(redisKey, uid, 1, TimeUnit.HOURS);
        String oldKey = stringRedis.opsForValue().get(tokenUidKey);
        if (!StringUtil.isNullOrEmpty(oldKey)) {
            log.info("删除旧key...{}",oldKey);
            stringRedis.delete(oldKey);
        }
        stringRedis.opsForValue().set(tokenUidKey, redisKey, 1, TimeUnit.HOURS);
        return token;
    }

    public int checkLogin(String token) {
        Integer redisValue = stringIntegerValueOperations.get(RedisKey.TOKEN_PREFIX + token);
        if (redisValue == null) {
            return INVALID_UID;
        }
        try {
            return redisValue;
        } catch (Exception ex) {
            log.error("parse uid error", ex);
        }
        return INVALID_UID;
    }
}
