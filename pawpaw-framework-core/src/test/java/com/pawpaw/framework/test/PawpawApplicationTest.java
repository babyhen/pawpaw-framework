package com.pawpaw.framework.test;

import com.pawpaw.framework.core.PawpawApplication;
import com.pawpaw.userserver.controller.IUserController;
import com.pawpaw.userserver.controller.vo.RegistUserResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.GetMapping;

@RunWith(SpringRunner.class)
@SpringBootApplication
@EnableFeignClients("com.pawpaw.userserver")
@Controller
public class PawpawApplicationTest {

    @Autowired
    private IUserController controller;

    public static void main(String[] args) {
        new PawpawApplicationTest().test();
    }

    @Test
    public void test() {
        PawpawApplication app = new PawpawApplication();
        app.run(PawpawApplicationTest.class);
    }


    @GetMapping("/testFeign")
    public void testFeign() {
        RegistUserResp resp = this.controller.registByMobile(null, "18810765135", "六");
        System.out.println(resp);
    }

}
