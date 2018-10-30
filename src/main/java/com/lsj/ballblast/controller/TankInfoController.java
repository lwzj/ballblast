package com.lsj.ballblast.controller;

import com.lsj.ballblast.annotation.CheckLogin;
import com.lsj.ballblast.annotation.LoginUid;
import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.service.ScoreRecordService;
import com.lsj.ballblast.service.TankInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@Api
@RequestMapping("game")
public class TankInfoController {
    @Autowired
    private TankInfoService tankInfoService;
    @Autowired
    private ScoreRecordService scoreRecordService;
    @ApiOperation("用户通关")
    @PostMapping("passLevel")
    @CheckLogin
    public Result passLevel(@LoginUid Integer uid,
                            @RequestParam("gold") Integer gold,
                            @RequestParam("score") Integer score,
                            @RequestParam("time") Integer time) {
        Result result = tankInfoService.passLevel(uid, gold, score, time);
        return result;
    }


    @ApiOperation("坦克死亡")
    @PostMapping("death")
    @CheckLogin
    public Result death(@LoginUid Integer uid,
                            @RequestParam("gold") Integer gold,
                            @RequestParam("score") Integer score,
                            @RequestParam("time") Integer time) {
        return tankInfoService.death(uid,gold,score,time);
    }

    @ApiOperation("升级坦克，火力射速一起升")
    @PostMapping("upgrade")
    @CheckLogin
    public Result upGrade(@LoginUid Integer uid) {
        return tankInfoService.upGrade(uid);
    }

//    @ApiOperation("升级火力")
//    @PostMapping("upPower")
//    @CheckLogin
//    public Result upPower(@LoginUid Integer uid) {
//        return tankInfoService.upPower(uid);
//    }
//

//    @ApiOperation("升级射速")
//    @PostMapping("upSpeed")
//    @CheckLogin
//    public Result upSpeed(@LoginUid Integer uid) {
//        return tankInfoService.upSpeed(uid);
//    }
}
