package com.pawpaw.framework.test;

import com.pawpaw.framework.core.PawpawApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootApplication
@EnableFeignClients("com.pawpaw.userserver")
@RestController
public class PawpawApplicationTest {


    public static void main(String[] args) {
        new PawpawApplicationTest().test();
    }

    @Test
    public void test() {
        PawpawApplication app = new PawpawApplication();
        app.run(PawpawApplicationTest.class);
    }


}
