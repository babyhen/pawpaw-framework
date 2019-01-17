package com.pawpaw.framework.core.test;

import com.pawpaw.framework.core.PawpawApplication;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
