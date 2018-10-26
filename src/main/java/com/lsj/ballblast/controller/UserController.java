package com.lsj.ballblast.controller;

import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Log4j2
@Api
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

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
        // TODO: 2018/10/25  检验登陆
        String ip  = "127.0.0.1";
        return userService.login(code, encryptedData, iv, channel, ip, shareImg, uid, version);
    }
}
