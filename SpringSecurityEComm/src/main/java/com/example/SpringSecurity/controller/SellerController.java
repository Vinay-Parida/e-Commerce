package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CategoryDAO;
import com.example.SpringSecurity.dao.CustomerDAO;
import com.example.SpringSecurity.dao.ProductDAO;
import com.example.SpringSecurity.dao.SellerDAO;
import com.example.SpringSecurity.dto.AddProductDTO;
import com.example.SpringSecurity.dto.CategoryForSellerDTO;
import com.example.SpringSecurity.dto.SellerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerDAO sellerDao;

    @Autowired
    CustomerDAO customerDao;

    @Autowired
    CategoryDAO categoryDao;

    @Autowired
    ProductDAO productDao;

    @GetMapping("/profile")
    public SellerProfileDTO getSellerProfile(HttpServletRequest httpServletRequest){
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

    @GetMapping("/getAllCategories")
    public List<CategoryForSellerDTO> getAllCategories(){
        return categoryDao.getCategoryForSeller();
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody AddProductDTO addProductDto, HttpServletRequest httpServletRequest, WebRequest webRequest){
        return productDao.addProduct(addProductDto, httpServletRequest, webRequest);
    }

}
