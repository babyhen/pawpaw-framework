package com.pawpaw.framework.core;

import com.pawpaw.framework.core.event.EventBroadcaster;
import com.pawpaw.framework.core.event.LogListener;
import com.pawpaw.framework.core.event.SimpleEvent;
import org.junit.Test;

public class EventTest {


    @Test
    public void parse() {
        String dir = "C:\\Users\\liujixin\\Desktop";
        EventBroadcaster.getInstance().regist(new LogListener(dir));
        EventBroadcaster.getInstance().broadcastEvent(new SimpleEvent("关莹是猪"));
    }
}




