package com.pawpaw.framework.test;

import com.pawpaw.framework.core.PawpawApplication;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
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

    @ApiOperation("测试swagger")
    @GetMapping("/swagger/test")
    public int swaggerTest() {
        return 1;
    }
}
