package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CustomerDao;
import com.example.SpringSecurity.dao.SellerDao;
import com.example.SpringSecurity.dto.CustomerDto;
import com.example.SpringSecurity.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    SellerDao sellerDao;

    @PostMapping("/customer")
    public String customerRegister(@Valid @RequestBody CustomerDto customerDto, WebRequest webRequest){
        return customerDao.registerCustomer(customerDto, webRequest);
    }

    @PostMapping("/seller")
    public String sellerRegister(@Valid @RequestBody SellerDto sellerDto, WebRequest webRequest){
        return sellerDao.registerSeller(sellerDto, webRequest);
    }
}
