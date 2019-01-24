package com.pawpaw.framework.core.config;

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


@Configuration
@Import({PawpawFrameworkConfigProperty.class, PawpawWebConfig.class, PawpawFeignConfig.class})
public class PawpawFrameworkAutoConfig {
    private static final Logger logger = LoggerFactory.getLogger(PawpawFrameworkAutoConfig.class);

    /**
     * 在jar文件的目录生成pid文件，标志当前进程的id
     */
    @PostConstruct
    public void genPidFile() throws IOException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("获取进程id失败");
        }
        String pid = name.split("@")[0];
        if (StringUtils.isBlank(pid)) {
            throw new RuntimeException("获取进程id失败");
        }
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        if (StringUtils.contains(path, ".jar!")) {
            int i = StringUtils.indexOf(path, ".jar!");
            path = StringUtils.substring(path, 0, i + 4);
            logger.info("jar file path :{}", path);
        }


        File f = new File(path);
        File parentFile = f.getParentFile();
        File pidFile = new File(parentFile, "PID");
        if (pidFile.exists()) {
            pidFile.delete();
        }
        pidFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(pidFile);
        IOUtils.write(pid, fos);
        IOUtils.closeQuietly(fos);

    }


    public static  void main(String[] args) throws Exception {
        File f = new File("file:/Users/jixinliu/projects/pawpaw/registry-server/target/registry-server-1.0.0.jar");
        File parentFile = f.getParentFile();
        System.out.println(parentFile);
        System.out.println(parentFile.exists());
        File pidFile = new File(parentFile, "PID");
        if (pidFile.exists()) {
            pidFile.delete();
        }
        pidFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(pidFile);
        IOUtils.write("123", fos);
        IOUtils.closeQuietly(fos);


    }

}
