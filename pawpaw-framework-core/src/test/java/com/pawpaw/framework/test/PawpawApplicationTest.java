package com.pawpaw.framework.test;

import com.pawpaw.framework.PawpawApplication;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PawpawApplicationTest {

    @Test
    public void test() {
        PawpawApplication app = new PawpawApplication();
        app.run(PawpawApplicationTest.class);
    }


}
