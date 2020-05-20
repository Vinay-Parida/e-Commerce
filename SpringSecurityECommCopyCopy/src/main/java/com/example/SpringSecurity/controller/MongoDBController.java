package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.CustomLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoDBController {

    @Autowired
    private CustomLoggerService customerLogger;

    @GetMapping("/logger")
    public String someFunction(){

        customerLogger.info("Executing Logger through extend", this.getClass());

        return "Success";

    }
}
