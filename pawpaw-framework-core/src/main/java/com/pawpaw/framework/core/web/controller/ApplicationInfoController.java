package com.pawpaw.framework.core.web.controller;

import com.pawpaw.framework.core.web.controller.vo.ApplicationInfoStatisticVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/application/info")
public class ApplicationInfoController {


    @GetMapping("/statistic")
    public ApplicationInfoStatisticVo statistic(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(1);

        return new ApplicationInfoStatisticVo();
    }


}
