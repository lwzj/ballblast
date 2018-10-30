package com.lsj.ballblast.annotation;

import com.lsj.ballblast.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/11 15:37
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class LoginUidArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
    @Autowired
    private TokenService tokenService;

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        return new NamedValueInfo(StringUtil.LOGIN_TOKEN, true, "-1");
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) {
        String[] values = request.getHeaderValues(name);
        if (values == null || values.length != 1) {
            return StringUtil.EMPTY;
        } else {
            return tokenService.checkLogin(values[0]);
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUid.class);
    }
}
