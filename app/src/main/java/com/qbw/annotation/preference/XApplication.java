package com.qbw.annotation.preference;

import android.app.Application;

import com.qbw.annotation.Preference;
import com.qbw.log.XLog;

/**
 * @author QBW
 * @createtime 2016/08/15 17:51
 * @company 9zhitx.com
 * @description
 */


public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Preference.init(this);
        XLog.setDebug(true);
        XLog.setSaveToFile("Preference");
    }
}
