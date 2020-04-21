package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CategoryDAO;
import com.example.SpringSecurity.dao.CustomerDAO;
import com.example.SpringSecurity.dao.ProductDAO;
import com.example.SpringSecurity.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerDAO customerDao;

    @Autowired
    private CategoryDAO categoryDao;

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/profile")
    public CustomerProfileDTO viewProfile(HttpServletRequest httpServletRequest){
        return customerDao.getProfile(httpServletRequest);
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressDTO addressDto, HttpServletRequest httpServletRequest){
        return customerDao.addAddress(addressDto, httpServletRequest);
    }

    @GetMapping("/address")
    public List<AddressDTO> getAddressList(HttpServletRequest httpServletRequest){
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

    @GetMapping("/getAllCategories")
    public List<CategoryDTO> getAllCategory(@RequestParam("CategoryId")Long id){
        return categoryDao.getAllCategoryForCustomer(id);
    }

    @GetMapping("/filterCategory")
    public CategoryFilterDTO filterCategory(@RequestParam("CategoryId") Long categoryId, WebRequest webRequest){
        return categoryDao.getFilterData(categoryId,webRequest);
    }

    @GetMapping("/viewProduct")
    public ViewCustomerProductDTO viewProductForCustomer(@RequestParam("productId") Long productId, WebRequest webRequest){
        return productDAO.viewCustomerProduct(productId, webRequest);
    }

    @GetMapping("/viewAllProduct")
    public List<ViewCustomerAllProductDTO> viewAllProductForCustomer(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order,
            @RequestParam("categoryId") Long categoryId,
            WebRequest webRequest
    ){
        return productDAO.viewCustomerAllProduct(categoryId,offset,webRequest,max,field,order);
    }

    @GetMapping(value = "/viewSimilarProduct")
    public List<ViewCustomerAllProductDTO> viewSimilarProduct(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order,
            @RequestParam("productId") Long productId,
            WebRequest webRequest
    ){
        return productDAO.viewSimilarProduct(productId,webRequest,offset,max,field,order);
    }



}
