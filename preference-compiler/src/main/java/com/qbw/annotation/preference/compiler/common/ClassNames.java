package com.qbw.annotation.preference.compiler.common;

import com.squareup.javapoet.ClassName;

import static com.qbw.annotation.preference.Constant.LINK;
import static com.qbw.annotation.preference.Constant.POET_CLASS_PACKAGE;
import static com.qbw.annotation.preference.Constant.REFERENCE_CLASS_PACKAGE;
import static com.qbw.annotation.preference.Constant.SUFFIX;

/**
 * Created by Bond on 2016/8/14.
 */

public class ClassNames {
    //custom(extern class)
    public static final ClassName IPREFERENCE = ClassName.get(REFERENCE_CLASS_PACKAGE, "IParasite");
    public static final ClassName IPREFERENCE_FLAG = ClassName.get(REFERENCE_CLASS_PACKAGE, "IHost");
    public static final ClassName PREFERENCE_UTIL = ClassName.get(REFERENCE_CLASS_PACKAGE, "PreferenceUtil");
    public static final ClassName PARASITE_MANAGER = ClassName.get(REFERENCE_CLASS_PACKAGE, "ParasiteManager");

    //android
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");

    //to generate
    public static final ClassName PREFERENCE = ClassName.get(POET_CLASS_PACKAGE, "Preference");
    public static final ClassName INIT = ClassName.get(POET_CLASS_PACKAGE, "INIT" + LINK + SUFFIX);
}
