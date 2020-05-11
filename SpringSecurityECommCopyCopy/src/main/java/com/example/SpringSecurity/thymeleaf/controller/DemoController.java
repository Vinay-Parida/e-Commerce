package com.example.SpringSecurity.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class DemoController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("dateNow", new Date());

        return "helloWorld";
    }
}
