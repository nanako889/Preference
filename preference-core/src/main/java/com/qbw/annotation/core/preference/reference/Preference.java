package com.qbw.annotation.core.preference.reference;

import android.content.Context;

import com.qbw.annotation.core.preference.ParasiteManager;

public class Preference {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void save(Object target) {
        ParasiteManager.getInstance().save(target);
    }

    public static void restore(Object target) {
        ParasiteManager.getInstance().restore(target);
    }
}
