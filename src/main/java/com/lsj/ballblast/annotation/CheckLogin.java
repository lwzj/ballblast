package com.lsj.ballblast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lei on 01/06/2017.
 * Yes, you can.
 * <p>
 * 用于标记方法
 * 表示该方法限制符合IP要求的用户才能调用，用户ADMIN相关接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
}
