package com.qbw.annotation.preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author QBW
 * @createtime 2016/08/22 10:56
 * @company 9zhitx.com
 * @description
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface SharedPreference {
}
