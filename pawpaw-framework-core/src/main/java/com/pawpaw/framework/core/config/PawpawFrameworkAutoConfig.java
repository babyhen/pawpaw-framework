package com.pawpaw.framework.core.config;

import com.pawpaw.framework.core.common.util.FileUtil;
import com.pawpaw.framework.core.feign.PawpawFeignConfig;
import com.pawpaw.framework.core.web.PawpawWebConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;


@Configuration
@Import({PawpawFrameworkConfigProperty.class, PawpawWebConfig.class, PawpawFeignConfig.class})
public class PawpawFrameworkAutoConfig {
    private static final Logger logger = LoggerFactory.getLogger(PawpawFrameworkAutoConfig.class);

    /**
     * 在jar文件的目录生成pid文件，标志当前进程的id
     */
    @PostConstruct
    public void genPidFile() throws IOException, URISyntaxException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("获取进程id失败");
        }
        String pid = name.split("@")[0];
        if (StringUtils.isBlank(pid)) {
            throw new RuntimeException("获取进程id失败");
        }

        String jarFile = FileUtil.getJarFilePath();
        String parent = new File(jarFile).getParent();
        logger.info("generate pid file at dir {} for pid {}", parent, pid);
        FileOutputStream fos = new FileOutputStream(new File(parent, "PID"));
        IOUtils.write(pid, fos);
        IOUtils.closeQuietly(fos);

    }


}
