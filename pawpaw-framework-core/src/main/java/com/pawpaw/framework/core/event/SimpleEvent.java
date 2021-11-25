package com.pawpaw.framework.core.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleEvent implements IEvent {

    private String str;


    @Override
    public String desc() {
        if (str == null) {
            return "";
        }
        return str;
    }
}
