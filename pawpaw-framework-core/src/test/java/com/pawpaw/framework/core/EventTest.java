package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.event.EventBroadcaster;
import com.pawpaw.framework.core.event.FileEventListener;
import com.pawpaw.framework.core.event.IEvent;
import org.junit.Test;

public class EventTest {


    @Test
    public void parse() throws JsonProcessingException {
        String dir = "C:\\Users\\liujixin\\Desktop";
        EventBroadcaster.getInstance().regist(new FileEventListener(dir));
        EventBroadcaster.getInstance().broadcastEvent(new IEvent() {
            @Override
            public String desc() {
                return "关莹是猪";
            }
        });
    }
}




