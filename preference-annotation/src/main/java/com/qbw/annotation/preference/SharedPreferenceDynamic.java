package com.qbw.annotation.preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qinbaowei
 * @date 2017/8/18
 * @email qbaowei@qq.com
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface SharedPreferenceDynamic {}
