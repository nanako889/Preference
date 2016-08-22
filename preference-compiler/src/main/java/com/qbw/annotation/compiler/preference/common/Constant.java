package com.qbw.annotation.compiler.preference.common;

/**
 * @author QBW
 * @createtime 2016/08/15 16:05
 * @company 9zhitx.com
 * @description
 */


public class Constant {

    public static final String REFERENCE_CLASS_PACKAGE = "com.qbw.annotation.core.preference.reference";
    public static final String POET_CLASS_PACKAGE = "com.qbw.annotation";
    public static final String LINK = "$$";
    public static final String SUFFIX = "PREFERENCE";
    public static final String SPLIT = "<_#$;_>";
    public static final String INNER_LINK = "_";
    public static final String SN_STRING = "String";
    public static final String SN_INTEGER = "Integer";
    public static final String SN_LONG = "Long";
    public static final String SN_FLOAT = "Float";
    public static final String SN_BOOLEAN = "Boolean";
    public static final String SN_STRING_ARR = "String[]";

    public static String appendSuffix(String className) {
        return className + LINK + SUFFIX;
    }
}
