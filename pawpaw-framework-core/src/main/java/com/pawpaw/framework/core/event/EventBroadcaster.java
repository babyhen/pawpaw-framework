package com.pawpaw.framework.core.event;

import java.util.LinkedList;
import java.util.List;

/**
 * 应该是单例的
 */
public class EventBroadcaster {

    private static volatile EventBroadcaster instance;

    public static EventBroadcaster getInstance() {
        if (instance == null) {
            synchronized (EventBroadcaster.class) {
                if (instance == null) {
                    instance = new EventBroadcaster();
                }
            }
        }
        return instance;
    }

    private final List<IEventListener> listeners;

    private EventBroadcaster() {
        this.listeners = new LinkedList<>();
    }

    public void regist(IEventListener listener) {
        listeners.add(listener);
    }

    public void broadcastEvent(IEvent event) {
        for (IEventListener listener : this.listeners) {
            listener.onEvent(event);
        }
    }
}
