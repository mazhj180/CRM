package com.mazhj.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TranController {

    @RequestMapping("/workbench/transaction/index.do")
    public String index(){
        return "/WEB-INF/pages/workbench/transaction/index.jsp";
    }
}
