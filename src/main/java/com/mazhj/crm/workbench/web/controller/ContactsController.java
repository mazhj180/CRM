package com.mazhj.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactsController {

    @RequestMapping("/workbench/contacts/index.do")
    public String index(){
        return "/WEB-INF/pages/workbench/contacts/index.jsp";
    }
}
