package com.pawpaw.framework.core.event;

import com.pawpaw.common.util.DateTimeUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class LogListener implements IEventListener {

    private final File fileDir;

    public LogListener(String fileDir) {
        File file = new File(fileDir);
        if (!file.isDirectory()) {
            throw new RuntimeException(fileDir + "不是一个有效目录");
        }
        this.fileDir = file;
        String fileName = this.getFileName();
        File fullPath = new File(this.fileDir, fileName);
        if (!fullPath.exists()) {
            try {
                fullPath.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


    private String getFileName() {
        String date = DateTimeUtil.format10(new Date());
        String fileName = date + ".txt";
        return fileName;
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
        String fileName = this.getFileName();
        File fullPath = new File(this.fileDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fullPath, true);
            IOUtils.write(sb.toString(), fos, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }


    }
}
