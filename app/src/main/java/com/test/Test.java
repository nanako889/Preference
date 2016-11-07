package com.test;

import com.qbw.annotation.preference.SharedPreference;

/**
 * @author QBW
 * @createtime 2016/08/16 18:41
 */


public class Test {

    @SharedPreference
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
