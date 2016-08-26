package com.test;

import com.qbw.annotation.preference.core.IHost;
import com.qbw.annotation.preference.SharedPreference;

/**
 * @author QBW
 * @createtime 2016/08/16 18:41
 * @company 9zhitx.com
 * @description
 */


public class Test implements IHost {

    @SharedPreference
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
