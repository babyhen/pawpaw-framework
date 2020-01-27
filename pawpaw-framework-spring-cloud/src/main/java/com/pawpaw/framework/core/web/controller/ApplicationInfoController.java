package com.pawpaw.framework.core.web.controller;

import com.pawpaw.framework.core.web.controller.vo.ApplicationInfoStatisticVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/application")
public class ApplicationInfoController {


    @GetMapping("/info/statistic")
    public ApplicationInfoStatisticVo statistic(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(1);

        return new ApplicationInfoStatisticVo();
    }


    @PostMapping("/shutdown")
    public void shutdown(HttpServletResponse response) throws IOException {
        System.out.println("application is going to shutdown");
        response.getWriter().write("success");
        response.getWriter().flush();
        System.exit(0);
    }

}
