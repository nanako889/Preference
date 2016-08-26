package com.qbw.annotation.preference.compiler.common;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * @author QBW
 * @createtime 2016/08/15 09:57
 * @company 9zhitx.com
 * @description
 */


public class Log {

    private static Messager sMessager;

    public static void setMessager(Messager messager) {
        sMessager = messager;
    }

    private static void _l(Diagnostic.Kind kind, String log) {
        if (null != log && !"".equals(log)) {
            sMessager.printMessage(kind, log);
        }
    }

    public static void i(String log) {
        _l(Diagnostic.Kind.NOTE, log);
    }

    public static void w(Exception e) {
        String log = null == e ? "" : e.getLocalizedMessage();
        _l(Diagnostic.Kind.WARNING, log);
    }

    public static void e(String log) {
        _l(Diagnostic.Kind.ERROR, log);
    }
}
