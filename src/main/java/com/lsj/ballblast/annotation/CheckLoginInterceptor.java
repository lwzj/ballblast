package com.lsj.ballblast.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.lsj.ballblast.config.RedisKey;
import com.lsj.ballblast.enums.ResultEnum;
import com.lsj.ballblast.service.AppService;
import com.lsj.ballblast.utils.ResultUtil;
import com.lsj.ballblast.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/11 15:37
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AppService appService;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> stringIntegerValueOperations;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> stringStringValueOperations;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.hasMethodAnnotation(CheckLogin.class)) {
            return true;
        }
        String token = request.getHeader(StringUtil.LOGIN_TOKEN);
        if (Strings.isNullOrEmpty(token)) {
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(ResultEnum.NOT_LOGIN)));
            return false;
        }
        int uid = tokenService.checkLogin(token);
        if (uid < 0) {
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(ResultEnum.NOT_LOGIN)));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        String requestToken = request.getHeader(StringUtil.LOGIN_TOKEN);
        if (StringUtil.isNullOrEmpty(requestToken)) {
            return;
        }
        int uid = tokenService.checkLogin(requestToken);
        //添加用户到活跃用户,便于更新用户的最后活跃时间。
        if (uid > 0) {
            appService.addActiveUser(uid);

            stringIntegerValueOperations.set(RedisKey.HEART_CHECK_PREFIX + uid, uid, 3, TimeUnit.MINUTES);

            //延长用户token过期时间
            String redisKey = RedisKey.TOKEN_PREFIX + requestToken;
            String tokenUidKey = RedisKey.TOKEN_UID_PREFIX + uid;
            stringIntegerValueOperations.set(redisKey, uid, 1, TimeUnit.HOURS);
            stringStringValueOperations.set(tokenUidKey, redisKey, 1, TimeUnit.HOURS);
        }
    }
}
