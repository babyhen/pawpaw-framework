package com.pawpaw.framework.config.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/application/info")
public class ApplicationInfoController {


    @GetMapping("/statistic")
    public void m(HttpServletRequest request, HttpServletResponse response){
        System.out.print(1);
    }


}
