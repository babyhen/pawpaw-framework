package com.pawpaw.framework.core.web;


import com.pawpaw.framework.core.common.IEnumType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RemoteResultCode implements IEnumType {
    SUCC(1, "成功"), FAIL(9999, "失败");
    ///////////////////////////////////////////
    private final int value;
    private final String desc;
    private static Map<Integer, RemoteResultCode> map;

    RemoteResultCode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public Collection<? extends IEnumType> allValues() {
        RemoteResultCode[] arr = RemoteResultCode.values();
        return Arrays.asList(arr);
    }


    public static final RemoteResultCode fromValue(int value) {
        if (map == null) {
            synchronized (RemoteResultCode.class) {
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                    for (RemoteResultCode i : RemoteResultCode.values()) {
                        map.put(i.value, i);
                    }
                }
            }
        }
        RemoteResultCode v = map.get(value);
        if (v == null) {
            throw new IllegalArgumentException("获取RemoteResultCode枚举值失败，value：" + value);
        }
        return v;
    }

    /////////////////////////////////////////////////////////////////////////////
    public String getDesc() {
        return desc;
    }
}
