package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CustomerDao;
import com.example.SpringSecurity.dto.AddressDto;
import com.example.SpringSecurity.dto.CustomerProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping("/profile")
    public CustomerProfileDto viewProfile(HttpServletRequest httpServletRequest){
        return customerDao.getProfile(httpServletRequest);
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressDto addressDto, HttpServletRequest httpServletRequest){
        return customerDao.addAddress(addressDto, httpServletRequest);
    }

    @GetMapping("/address")
    public List<AddressDto> getAddressList(HttpServletRequest httpServletRequest){
        return customerDao.getAddressList(httpServletRequest);
    }

    @PutMapping("/updateProfile")
    public String updateProfile(@Valid @RequestBody HashMap<String, Object> stringObjectHashMap, HttpServletRequest httpServletRequest){
        return customerDao.updateProfile(stringObjectHashMap,httpServletRequest);
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, HttpServletRequest httpServletRequest){
        return customerDao.updatePassword(password, confirmPassword,httpServletRequest);
    }

    @DeleteMapping("/deleteAddress")
    public String deleteAddress(@RequestParam("Id") Long id, HttpServletRequest httpServletRequest){
        return customerDao.deleteAddress(id, httpServletRequest);
    }

    @PutMapping("/updateAddress")
    public String updateAddress(@RequestParam("addressId") Long id, @RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest) throws Exception {
        return customerDao.updateAddress(map, id, httpServletRequest);
    }

}
