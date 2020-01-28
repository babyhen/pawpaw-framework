package com.pawpaw.framework.core;

import com.pawpaw.framework.core.PawpawQuickStartApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootApplication
public class PawpawApplicationTest {


    @Test
    public void pawpawQuickStartApplication() {
        PawpawQuickStartApplication app = new PawpawQuickStartApplication();
        app.run(PawpawApplicationTest.class);
        System.out.println("quick start success");
    }

}
