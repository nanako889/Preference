package com.qbw.annotation.core.preference;

import com.qbw.annotation.core.preference.reference.IPreference;
import com.qbw.log.XLog;

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

    public static ParasiteManager getInstance() {
        if (null == sInstance) {
            synchronized (ParasiteManager.class) {
                if (null == sInstance) {
                    sInstance = new ParasiteManager();
                }
            }
        }
        return sInstance;
    }

    private Map<String, IPreference> mIPreferenceMap;

    private ParasiteManager() {
        mIPreferenceMap = new HashMap<>();
    }

    public void save(Object target) {
        Class cls = target.getClass();
        XLog.v("[preference]save [%s] data", cls.getName());
        IPreference iPreference = getPreference(cls.getName());
        if (null != iPreference) {
            iPreference.save(target);
        }
    }

    public void restore(Object target) {
        IPreference iPreference = getPreference(target.getClass().getName());
        if (null != iPreference) {
            iPreference.restore(target);
        }
    }

    private IPreference getPreference(String targetClassName) {
        String fileClassName = targetClassName.replace("$", Constant.INNER_LINK) + Constant.LINK + Constant.SUFFIX;
        IPreference iPreference = mIPreferenceMap.get(fileClassName);
        if (null == iPreference) {
            try {
                XLog.v("[preference]get [%s] instance", fileClassName);
                iPreference = (IPreference) Class.forName(fileClassName).newInstance();
            } catch (InstantiationException e) {
                XLog.e(e);
            } catch (IllegalAccessException e) {
                XLog.e(e);
            } catch (ClassNotFoundException e) {
                XLog.e("class[%s] has no PREFERENCE annotation!!!", targetClassName);
            } catch (Exception e) {
                XLog.e(e);
            } finally {
                if (null != iPreference) {
                    mIPreferenceMap.put(targetClassName, iPreference);
                }
            }
        }
        return iPreference;
    }
}
