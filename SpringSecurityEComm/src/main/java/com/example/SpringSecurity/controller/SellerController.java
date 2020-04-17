package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CustomerDao;
import com.example.SpringSecurity.dao.SellerDao;
import com.example.SpringSecurity.dto.SellerProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerDao sellerDao;

    @Autowired
    CustomerDao customerDao;

    @GetMapping("/profile")
    public SellerProfileDto getSellerProfile(HttpServletRequest httpServletRequest){
        return sellerDao.getSellerProfile(httpServletRequest);
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@Param("password") String password, @Param("confirmPassword") String confirmPassword, HttpServletRequest httpServletRequest){
        return customerDao.updatePassword(password, confirmPassword, httpServletRequest);
    }

    @PutMapping("/updateAddress")
    public String updateAddress(@RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest) throws Exception {
        return sellerDao.updateSellerAddress(map, httpServletRequest);
    }

    @PutMapping("/updateProfile")
    public String updateProfile(@Valid @RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest){
        return sellerDao.updateProfile(map, httpServletRequest);
    }

}
