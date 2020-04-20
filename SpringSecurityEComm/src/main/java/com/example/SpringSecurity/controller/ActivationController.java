package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.ActivationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class ActivationController {

    @Autowired
    private ActivationDAO activationDao;

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest webRequest, @RequestParam("token") String token){
        return activationDao.activateUser(token, webRequest);
    }

    @PostMapping("/reactivateUser")
    public String reactivateUser(WebRequest webRequest, @RequestParam("email") String email){
        return activationDao.reactivationUser(email,webRequest);
    }
}
