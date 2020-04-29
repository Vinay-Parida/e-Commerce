package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.CustomerService;
import com.example.SpringSecurity.service.SellerService;
import com.example.SpringSecurity.dto.CustomerRegisterDTO;
import com.example.SpringSecurity.dto.SellerRegisterDTO;
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
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @PostMapping("/customer")
    public String customerRegister(@Valid @RequestBody CustomerRegisterDTO customerDto, WebRequest webRequest){
        return customerService.registerCustomer(customerDto, webRequest);
    }

    @PostMapping("/seller")
    public String sellerRegister(@Valid @RequestBody SellerRegisterDTO sellerDto, WebRequest webRequest){
        return sellerService.registerSeller(sellerDto, webRequest);
    }
}
