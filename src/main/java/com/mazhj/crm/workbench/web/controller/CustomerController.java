package com.mazhj.crm.workbench.web.controller;

import com.mazhj.crm.workbench.pojo.Activity;
import com.mazhj.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/workbench/customer/index.do")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/customer/index.jsp");
        return modelAndView;
    }



}
