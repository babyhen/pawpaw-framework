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
        boolean handled = false;
        for (IEventListener listener : this.listeners) {
            boolean canHandle = listener.canHandle(event);
            if (canHandle) {
                listener.onEvent(event);
                handled = true;
            }

        }
        //没有能够处理的，默认打印到控制台
        if (!handled && event != null) {
            System.out.println("没有listener可以处理此event:" + event.desc());
        }
    }
}
