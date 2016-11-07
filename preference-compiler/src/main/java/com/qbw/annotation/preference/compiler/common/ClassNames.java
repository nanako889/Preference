package com.qbw.annotation.preference.compiler.common;

import com.squareup.javapoet.ClassName;

import static com.qbw.annotation.preference.Constant.LINK;
import static com.qbw.annotation.preference.Constant.GENERATE_CLASS_PACKAGE;
import static com.qbw.annotation.preference.Constant.SUFFIX;

/**
 * Created by Bond on 2016/8/14.
 */

public class ClassNames {
    //custom(extern class)
    public static final ClassName PREFERENCE_UTIL = ClassName.get(GENERATE_CLASS_PACKAGE, "PreferenceUtil");

    //android
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");

    //java
    public static final ClassName STRING = ClassName.get(String.class);

    //to generate
    public static final ClassName PREFERENCE = ClassName.get(GENERATE_CLASS_PACKAGE, "Preference");
    public static final ClassName INIT = ClassName.get(GENERATE_CLASS_PACKAGE, "INIT" + LINK + SUFFIX);
}
