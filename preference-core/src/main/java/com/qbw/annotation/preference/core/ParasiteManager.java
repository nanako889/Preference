package com.qbw.annotation.preference.core;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QBW
 * @createtime 2016/08/15 19:25
 * @company 9zhitx.com
 * @description
 */


public class ParasiteManager {

    private static ParasiteManager sInstance;

    public static ParasiteManager getInstance(Context context) {
        if (null == sInstance) {
            synchronized (ParasiteManager.class) {
                if (null == sInstance) {
                    sInstance = new ParasiteManager(context);
                }
            }
        }
        return sInstance;
    }

    private Map<String, IParasite> mIPreferenceMap;
    private PreferenceUtil mPreferenceUtil;

    private ParasiteManager(Context context) {
        mIPreferenceMap = new HashMap<>();
        mPreferenceUtil = PreferenceUtil.getInstance(context);
    }

    public void save(Object target) {
        IParasite iParasite = getPreference(target.getClass().getCanonicalName());
        if (null != iParasite) {
            iParasite.save(target);
        }
    }

    public void restore(Object target) {
        IParasite iParasite = getPreference(target.getClass().getCanonicalName());
        if (null != iParasite) {
            iParasite.restore(target);
        }
    }

    public void clear(Object target) {
        IParasite iParasite = getPreference(target.getClass().getCanonicalName());
        if (null != iParasite) {
            iParasite.clear(target);
        }
    }

    public void clearAll() {
        mPreferenceUtil.clear();
    }

    private IParasite getPreference(String hostCanonicalClassName) {
        return mIPreferenceMap.get(hostCanonicalClassName);
    }

    public void hostBrigeParasite(String hostCanonicalClassName, IParasite parasite) {
        mIPreferenceMap.put(hostCanonicalClassName, parasite);
    }
}
