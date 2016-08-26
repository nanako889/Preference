package com.qbw.annotation.preference.core;

/**
 * @author QBW
 * @createtime 2016/08/15 17:18
 * @company 9zhitx.com
 * @description
 */


public interface IParasite {
    void save(Object target);
    void restore(Object target);
    void clear(Object target);
}
