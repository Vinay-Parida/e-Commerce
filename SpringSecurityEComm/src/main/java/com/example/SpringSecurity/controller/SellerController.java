package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.CategoryDAO;
import com.example.SpringSecurity.dao.CustomerDAO;
import com.example.SpringSecurity.dao.ProductDAO;
import com.example.SpringSecurity.dao.SellerDAO;
import com.example.SpringSecurity.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping(value = "/addProductVariation", consumes = {"multipart/form-data"})
    public String addProductVariation(@RequestPart("primaryImage")MultipartFile primaryimage,
                                      @RequestPart("secondaryImages") List<MultipartFile> secondaryImages,
                                      HttpServletRequest httpServletRequest,
                                      @RequestPart("productVariation")AddProductVariationDTO addProductVariationDTO,
                                      WebRequest webRequest) throws IOException {
        return productDao.addProductVariation(primaryimage, secondaryImages, httpServletRequest, addProductVariationDTO, webRequest);
    }

    @GetMapping("/viewProduct")
    private ViewProductDTO viewProduct(@RequestParam("productId") Long productId, WebRequest webRequest, HttpServletRequest request){
        return productDao.viewProduct(productId, webRequest,request);
    }

    @GetMapping("/viewProductVariation")
    private ViewProductVariationDTO viewProductVariation(@RequestParam("variationId") Long variationId, WebRequest webRequest, HttpServletRequest request){
        return productDao.viewProductVariation(variationId, webRequest,request);
    }

}
