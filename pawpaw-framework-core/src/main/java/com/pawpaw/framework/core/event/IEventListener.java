package com.pawpaw.framework.core.event;

public interface IEventListener {
    boolean canHandle(IEvent event);

    void onEvent(IEvent event);

}
