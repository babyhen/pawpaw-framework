package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.event.EventBroadcaster;
import org.junit.Test;

public class EventBroadcasterTest {


    @Test
    public void configAsync() throws JsonProcessingException {

        EventBroadcaster.getInstance().configAsync(false);
        EventBroadcaster.getInstance().configAsync(false);
        EventBroadcaster.getInstance().configAsync(true);
        EventBroadcaster.getInstance().configAsync(true);

    }
}




