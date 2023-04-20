package com.mazhj.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        System.out.println("welcome");
        return "/WEB-INF/pages/index.jsp";
    }
}
