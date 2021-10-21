package com.pawpaw.framework.core.event;

import com.pawpaw.common.util.DateTimeUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ConsolePrintEventListener implements IEventListener {

    @Override
    public void onEvent(IEvent event) {
        System.out.println(event.desc());
    }
}
