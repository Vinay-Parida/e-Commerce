package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.AdminDao;
import com.example.SpringSecurity.dto.FindAllCustomerDto;
import com.example.SpringSecurity.dto.FindAllSellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("/customers")
    public List<FindAllCustomerDto> getCustomersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "0") Integer pageOffset,
                                                     @RequestParam(defaultValue = "id") String sortByField){
        return adminDao.getCustomersList(pageSize, pageOffset,sortByField);
    }


    @GetMapping("/sellers")
    public List<FindAllSellerDto> getSellersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "0") Integer pageOffset,
                                                 @RequestParam(defaultValue = "id") String sortByField){
        return adminDao.getSellersList(pageSize, pageOffset, sortByField);
    }

    @PutMapping("activate/customer")
    public String activateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.activateCustomer(id, webRequest);
    }

    @PutMapping("deactivate/customer")
    public String deactivateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.deactivateCustomer(id, webRequest);
    }

    @PutMapping("activate/seller")
    public String activateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.activateSeller(id, webRequest);
    }

    @PutMapping("deactivate/seller")
    public String deactivateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.deactivateSeller(id, webRequest);
    }
}
