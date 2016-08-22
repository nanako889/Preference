package com.qbw.annotation.compiler.preference.common;

import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

import static com.qbw.annotation.compiler.preference.common.Constant.POET_CLASS_PACKAGE;
import static com.qbw.annotation.compiler.preference.common.Constant.REFERENCE_CLASS_PACKAGE;

/**
 * Created by Bond on 2016/8/14.
 */

public class ClassNames {

    //custom(extern class)
    public static final ClassName PREFERENCE = ClassName.get(REFERENCE_CLASS_PACKAGE, "Preference");
    public static final ClassName IPREFERENCE = ClassName.get(REFERENCE_CLASS_PACKAGE, "IPreference");

    //to generate
    public static final ClassName PREFERENCE_UTIL = ClassName.get(POET_CLASS_PACKAGE, Constant.appendSuffix("PREUTIL"));
    //public static final ClassName IPREFERENCE = ClassName.get(POET_CLASS_PACKAGE, Constant.appendSuffix("IPRE"));
    public static final ClassName PREFERENCE_HELPER = ClassName.get(POET_CLASS_PACKAGE, Constant.appendSuffix("PREHELPER"));

    //android
    public static final ClassName SHARED_PREFERENCE = ClassName.get("android.content", "SharedPreferences");
    public static final ClassName SHARED_PREFERENCE_EDITOR = ClassName.get("android.content", "SharedPreferences.Editor");
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");

    //java
    public static final ClassName STRING = ClassName.get(String.class);
    public static final ClassName MAP = ClassName.get(Map.class);
    public static final ClassName HASHMAP = ClassName.get(HashMap.class);
    public static final ClassName EXCEPTION = ClassName.get(Exception.class);

}
