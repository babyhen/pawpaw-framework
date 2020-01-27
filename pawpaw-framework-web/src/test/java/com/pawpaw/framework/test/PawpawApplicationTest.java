package com.pawpaw.framework.test;

import com.pawpaw.framework.core.PawpawQuickStartApplication;
import com.pawpaw.framework.core.PawpawSpringCloudApplication;
import com.pawpaw.framework.core.PawpawWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootApplication
public class PawpawApplicationTest {


    @Test
    public void pawpawSpringCloudApplication() {
        PawpawSpringCloudApplication app = new PawpawSpringCloudApplication();
        app.run(PawpawApplicationTest.class);
        System.out.println("cloud application started");
    }



    @Test
    public void pawpawQuickStartApplication() {
        PawpawQuickStartApplication app = new PawpawQuickStartApplication();
        app.run(PawpawApplicationTest.class);
        System.out.println("quick start success");
    }

    @Test
    public void pawpawWebApplication() {
        PawpawWebApplication app = new PawpawWebApplication();
        app.run(PawpawApplicationTest.class);
        System.out.println("web application success");
    }


}
