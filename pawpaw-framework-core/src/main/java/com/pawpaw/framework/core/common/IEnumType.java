package com.pawpaw.framework.core.common;

import java.util.Collection;

/**
 * 通过的枚举接口
 *
 * @author liujixin
 */
public interface IEnumType {
    public  int value();

    public Collection<? extends IEnumType> allValues();


}
