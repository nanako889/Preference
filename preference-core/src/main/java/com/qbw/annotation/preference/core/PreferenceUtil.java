package com.qbw.annotation.preference.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.qbw.log.XLog;

import static com.qbw.annotation.preference.Constant.SN_BOOLEAN;
import static com.qbw.annotation.preference.Constant.SN_FLOAT;
import static com.qbw.annotation.preference.Constant.SN_INTEGER;
import static com.qbw.annotation.preference.Constant.SN_LONG;
import static com.qbw.annotation.preference.Constant.SN_STRING;

/**
 * @author QBW
 * @createtime 2016/08/23 09:54
 * @company 9zhitx.com
 * @description
 */


public class PreferenceUtil {

    private static PreferenceUtil sInstance;

    private SharedPreferences mSP;

    private PreferenceUtil(Context context) {
        mSP = context.getSharedPreferences("PreferenceUtil$$QBW_ANNOTATION", Context.MODE_PRIVATE);
    }

    public static PreferenceUtil getInstance(Context context) {
        if (null == sInstance) {
            synchronized (PreferenceUtil.class) {
                if (null == sInstance) {
                    sInstance = new PreferenceUtil(context);
                }
            }
        }
        return sInstance;
    }

    public void put(String key, String valueClassName, Object value) {
        SharedPreferences.Editor e = mSP.edit();
        if (SN_INTEGER.equals(valueClassName)) {
            if (null == value) {
                e.putInt(key, 0);
            } else {
                int v = (int) value;
                e.putInt(key, v);
            }
        } else if (SN_LONG.equals(valueClassName)) {
            if (null == value) {
                e.putLong(key, 0L);
            } else {
                long v = (long) value;
                e.putLong(key, v);
            }
        } else if (SN_FLOAT.equals(valueClassName)) {
            if (null == value) {
                e.putFloat(key, 0.0F);
            } else {
                float v = (float) value;
                e.putFloat(key, v);
            }
        } else if (SN_BOOLEAN.equals(valueClassName)) {
            if (null == value) {
                e.putBoolean(key, false);
            } else {
                boolean v = (boolean) value;
                e.putBoolean(key, v);
            }
        } else if (SN_STRING.equals(valueClassName)) {
            if (null == value) {
                e.putString(key, "");
            } else {
                String v = (String) value;
                e.putString(key, v);
            }
        }
        XLog.d("%s = %s", key, value);
        e.commit();
    }

    public Object get(String key, String valueClassName) {
        Object value = null;
        if (SN_INTEGER.equals(valueClassName)) {
            value = mSP.getInt(key, 0);
        } else if (SN_LONG.equals(valueClassName)) {
            value = mSP.getLong(key, 0L);
        } else if (SN_FLOAT.equals(valueClassName)) {
            value = mSP.getFloat(key, 0.0F);
        } else if (SN_BOOLEAN.equals(valueClassName)) {
            value = mSP.getBoolean(key, false);
        } else if (SN_STRING.equals(valueClassName)) {
            value = mSP.getString(key, "");
        }
        XLog.d("%s = %s", key, value);
        return value;
    }

    public void clear() {
        XLog.w("clear all @SharedPreference");
        mSP.edit().clear().commit();
    }

    public void remove(String key) {
        XLog.w("clear @SharedPreference %s", key);
        mSP.edit().remove(key).commit();
    }
}
