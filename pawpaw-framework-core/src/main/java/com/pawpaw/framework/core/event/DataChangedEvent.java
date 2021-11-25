package com.pawpaw.framework.core.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataChangedEvent implements IEvent {

    private Object old;
    private Object latest;

    @Override
    public String desc() {
        StringBuilder sb = new StringBuilder();
        sb.append("数据已改变,old:").append(old).append(",latest:").append(latest);
        return sb.toString();
    }
}
