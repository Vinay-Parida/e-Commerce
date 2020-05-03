//package com.example.SpringSecurity.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class LoggingController {
//
//    Logger logger = LoggerFactory.getLogger(LoggingController.class);
//
//    @GetMapping("/")
//    public String index(){
//        logger.trace("Tracing");
//        logger.debug("Debugging");
//        logger.info("Info");
//        logger.warn("Warning");
//        logger.error("Error");
//
//        return "Logs";
//    }
//}
