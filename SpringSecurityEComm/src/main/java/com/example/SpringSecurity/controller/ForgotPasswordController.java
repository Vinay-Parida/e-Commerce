package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.ForgetPasswordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class ForgotPasswordController {

    @Autowired
    ForgetPasswordDAO forgetPasswordDao;

    @PostMapping(value = "/forgetPassword")
    public String forgetPassword(@Param("email") String email, WebRequest webRequest){
        return forgetPasswordDao.sendForgetPasswordToken(email, webRequest);
    }

    @PutMapping(value = "/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword, WebRequest webRequest){
        return forgetPasswordDao.resetPassword(token, password, confirmPassword,webRequest);
    }
}
