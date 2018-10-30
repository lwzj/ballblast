package com.lsj.ballblast.controller;

import com.lsj.ballblast.annotation.CheckLogin;
import com.lsj.ballblast.annotation.LoginUid;
import com.lsj.ballblast.config.App;
import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.dto.RewardResp;
import com.lsj.ballblast.enums.ResultEnum;
import com.lsj.ballblast.service.TankInfoService;
import com.lsj.ballblast.service.UserService;
import com.lsj.ballblast.utils.HttpUtil;
import com.lsj.ballblast.utils.ResultUtil;
import com.lsj.ballblast.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Log4j2
@Api
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public Result test() {
        return ResultUtil.error(ResultEnum.TEST);
    }
    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login(@RequestParam("code") String code,
                        @RequestParam("encryptedData") String encryptedData,
                        @RequestParam("iv") String iv,
                        @RequestParam(value = "channel") String channel,
                        @RequestParam(value = "shareImg") String shareImg,
                        @RequestParam(value = "shareId", required = false) Integer uid,
                        @RequestParam(required = false) String version,
                        HttpServletRequest request) throws IOException{
        if (version != null && !App.getVersion().equals(version)) {
            App.setVersion(version);
        }
        String ip = HttpUtil.getIpAddr(request);
        log.info("code :{},encryptedData:{},iv:{},channel:{},ip:{},version:{}", code, encryptedData, iv, channel, ip, version);
        if (StringUtil.UNDEFINED.equals(encryptedData) || StringUtil.UNDEFINED.equals(iv))
            return ResultUtil.error(ResultEnum.PARAM_ERR);
        return userService.login(code, encryptedData, iv, channel, ip, shareImg, uid, version);
    }

    @ApiOperation("收集离线奖励")
    @PostMapping("reward")
    @CheckLogin
    public Result reward(@LoginUid Integer uid,
                         @RequestParam("gold") Integer gold) {
        int goldc = userService.getGold(uid, gold);
        return ResultUtil.success(RewardResp.builder()
        .goldCoin(goldc)
        .build());
    }

//    @ApiOperation("用户分享")
//    @PostMapping("share")
//    @CheckLogin
//    public Result share(@LoginUid int uid,
//                        @RequestParam("type") String type,
//                        @RequestParam("success") Boolean success,
//                        @RequestParam(value = "encryptedData", required = false) String encryptedData,
//                        @RequestParam(value = "iv", required = false) String iv) throws IOException {
//        log.info("type:{},success:{},encryptedData:{},iv:{}", type, success, encryptedData, iv);
//        if (!StringUtil.isNullOrEmpty(encryptedData) && !StringUtil.isNullOrEmpty(iv)) {
//            //分享到群  后台根据目前ABTest视情况发放奖励
////            return userService.share2Group(uid, encryptedData, iv, type, success);
//        } else {
//            //分享到个人 不会获得奖励
////            return userService.share(uid, type, success, false);
//        }
//    }

    @ApiOperation("排行榜")
    @PostMapping("rankingList")
    @CheckLogin
    public Result rankingList(@LoginUid Integer uid) {
        return null;
    }

}
