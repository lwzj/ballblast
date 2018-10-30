package com.lsj.ballblast.service.impl;

import com.lsj.ballblast.config.RedisKey;
import com.lsj.ballblast.dao.ScoreRecordMapper;
import com.lsj.ballblast.dao.TankInfoMapper;
import com.lsj.ballblast.dao.UpGradeGoldMapper;
import com.lsj.ballblast.dto.GameOverResp;
import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.dto.UpgradeResp;
import com.lsj.ballblast.enums.ResultEnum;
import com.lsj.ballblast.model.ScoreRecord;
import com.lsj.ballblast.model.TankInfo;
import com.lsj.ballblast.model.UpgradeGold;
import com.lsj.ballblast.service.TankInfoService;
import com.lsj.ballblast.utils.ResultUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Log4j2
public class TankInfoServiceImpl extends HandlerInterceptorAdapter implements TankInfoService  {

    @Autowired
    private TankInfoMapper tankInfoMapper;
    @Autowired
    private ScoreRecordMapper scoreRecordMapper;
    @Autowired
    private UpGradeGoldMapper upGradeGoldMapper;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> stringIntegerValueOperations;

    @Override
    public int insret(Integer uid) {

        return tankInfoMapper.insret(TankInfo.builder()
        .uid(uid)
        .gameLevel(1)
        .goldCoin(0)
        .level(1)
        .build());
    }

    @Override
    public TankInfo findByUid(Integer uid) {
        return tankInfoMapper.findByUid(uid);
    }

    @Override
    public Result passLevel(Integer uid, Integer gold, Integer score, Integer time) {
        TankInfo tankInfo = tankInfoMapper.findByUid(uid);
        int g = gold + tankInfo.getGoldCoin();
        int level = tankInfo.getGameLevel() + 1;
        //跟新关卡数 和金币数
        tankInfoMapper.updateLevel(uid, g,level);
        //记录当前数据
        ScoreRecord scoreRecord = ScoreRecord.builder()
                .score(score)
                .endtime(LocalDateTime.now())
                .gameLevel(tankInfo.getGameLevel())
                .time(time)
                .build();
        scoreRecordMapper.insret(scoreRecord);
        //跟新最高分
        Integer maxScore = 0;
        String redisKey = RedisKey.MAX_SCORE + uid;
        Integer rMaxScore = stringIntegerValueOperations.get(redisKey);
        if (score > rMaxScore) {
            stringIntegerValueOperations.set(redisKey, score);
            maxScore = score;
        } else {
            maxScore = rMaxScore;
        }
        //生成返回的对象信息
        GameOverResp resp = GameOverResp.builder()
                .gameLevel(level)
                .goldCoin(g)
                .maxScore(maxScore)
                .build();
        return ResultUtil.success(resp);
    }

    @Override
    public Result death(Integer uid, Integer gold, Integer score, Integer time) {
        TankInfo tankInfo = tankInfoMapper.findByUid(uid);
        int g = gold + tankInfo.getGoldCoin();
        int level = tankInfo.getGameLevel();
        //跟新关卡数 和金币数
        tankInfoMapper.updateLevel(uid, g,level);
        //记录当前数据
        ScoreRecord scoreRecord = ScoreRecord.builder()
                .score(score)
                .endtime(LocalDateTime.now())
                .gameLevel(tankInfo.getGameLevel())
                .time(time)
                .build();
        scoreRecordMapper.insret(scoreRecord);
        //跟新最高分
        Integer maxScore = 0;

        //生成返回的对象信息
        GameOverResp resp = GameOverResp.builder()
                .gameLevel(tankInfo.getGameLevel())
                .goldCoin(tankInfo.getGoldCoin())
                .maxScore(maxScore)
                .build();
        return ResultUtil.success(resp);
    }

    @Override
    public Result upGrade(Integer uid) {
        TankInfo tankOld = tankInfoMapper.findByUid(uid);
        UpgradeGold upGradeGold = upGradeGoldMapper.findGoldByLevel(tankOld.getLevel());
        Integer gold = upGradeGold.getGoldCoinCost();
        if (tankOld.getGoldCoin() >= gold) {
            tankOld.setLevel(tankOld.getLevel() + 1);
            tankOld.setGoldCoin(tankOld.getGoldCoin() - gold);
            tankInfoMapper.upGrade(tankOld);
            return ResultUtil.success(UpgradeResp.builder()
                    .goldAdd(upGradeGold.getGoldCoin())
                    .goldCoin(tankOld.getGoldCoin() - gold)
                    .goldCoinCost(upGradeGold.getGoldCoinCost())
                    .offlineL(upGradeGold.getOfflineIncome())
                    .power(upGradeGold.getPower())
                    .speed(upGradeGold.getSpeed())
                    .build());
        } else {
            return ResultUtil.error(ResultEnum.LACK_OF_GOLDCOIN);
        }
    }

    @Override
    public Result upPower(Integer uid) {
        return null;
    }

    @Override
    public Result upSpeed(Integer uid) {
        return null;
    }

}
