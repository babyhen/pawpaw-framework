package com.pawpaw.framework.core.event;

import com.pawpaw.common.util.DateTimeUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileEventListener implements IEventListener {

    private final File fileDir;

    public FileEventListener(String fileDir) {
        File file = new File(fileDir);
        if (!file.isDirectory()) {
            throw new RuntimeException(fileDir + "不是一个有效目录");
        }
        this.fileDir = file;
    }


    @Override
    public boolean canHandle(IEvent event) {
        return true;
    }

    @Override
    public void onEvent(IEvent event) {
        String desc = event.desc();
        if (desc == null) {
            desc = "";
        }
        String currTime = DateTimeUtil.format19(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(currTime);
        sb.append("--->");
        sb.append(desc);
        sb.append("\r\n");
        String date = DateTimeUtil.format10(new Date());
        String fileName = date + ".txt";
        File fullPath = new File(this.fileDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(fullPath, true);
            IOUtils.write(sb.toString(), fos, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
