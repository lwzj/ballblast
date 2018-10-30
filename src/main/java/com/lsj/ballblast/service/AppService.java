package com.lsj.ballblast.service;

import com.lsj.ballblast.config.App;
import com.lsj.ballblast.config.RedisKey;
import com.lsj.ballblast.dao.AppMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/31 21:00
 */
@Service
@Log4j2
public class AppService {
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private RedisTemplate<String, Set<Integer>> setRedis;


    //    @Async
    public void clickGuide(Integer uid, String guideType) {
        //先存到redis，每10分钟刷新一次
//        Integer num = stringIntegerValueOperations.get(RedisKey.GUIDE_PREFIX + guideType + StringUtil.UNDERLINE + LocalDate.now());
//        if (num == null) {
//            stringIntegerValueOperations.set(RedisKey.GUIDE_PREFIX + guideType + StringUtil.UNDERLINE + LocalDate.now(), 1, 1, TimeUnit.DAYS);
//        } else {
//            stringIntegerValueOperations.set(RedisKey.GUIDE_PREFIX + guideType + StringUtil.UNDERLINE + LocalDate.now(), num + 1, 1, TimeUnit.DAYS);
//        }
//        return ResultUtil.success();
        appMapper.updateGuide(uid, guideType);
    }

    @Async
    public void clickButton(Integer uid, String buttonName, String pageName, Integer duration) {
        appMapper.insertButtonClick(uid, buttonName, pageName, duration, App.getVersion());
    }

    public void addActiveUser(int uid) {
        Set<Integer> activeUids = setRedis.opsForValue().get(RedisKey.ACTIVE_USERS);
        if (activeUids == null) {
            activeUids = new HashSet<>();
        }
        activeUids.add(uid);
        setRedis.opsForValue().set(RedisKey.ACTIVE_USERS, activeUids, 1, TimeUnit.DAYS);
    }

    public void refreshActiveUser() {
        //从redis中取出活跃用户数据，然后清空redis数据;
        Set<Integer> activeUids = setRedis.opsForValue().get(RedisKey.ACTIVE_USERS);
        if (activeUids != null && activeUids.size() > 0) {
            //将活跃用户更新至数据库
            appMapper.updateActiveTime(activeUids);
            //清空redis的数据
            activeUids.clear();
            setRedis.opsForValue().set(RedisKey.ACTIVE_USERS, activeUids, 1, TimeUnit.DAYS);
        }
    }


}
