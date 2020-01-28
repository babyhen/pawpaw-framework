package com.pawpaw.framework.core.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static String getJarFilePath() throws URISyntaxException {
        String path = FileUtil.class.getResource("/").getPath();
        LOGGER.info("resource path {}", path);
        if (StringUtils.contains(path, ".jar")) {
            int i = path.indexOf(".jar");
            path = path.substring(0, i + 4);
        }
        URI u = new URI(path);
        path = u.getPath();
        return path;
    }

    public static void delFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childs = file.listFiles();
            for (int i = 0; i < childs.length; i++) {
                delFile(childs[i]);
            }
        }

    }
}
