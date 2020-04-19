package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.AdminDao;
import com.example.SpringSecurity.dao.CategoryDao;
import com.example.SpringSecurity.dto.CategoryMetadataFieldValueDto;
import com.example.SpringSecurity.dto.FindAllCustomerDto;
import com.example.SpringSecurity.dto.FindAllSellerDto;
import com.example.SpringSecurity.dto.SingleCategoryDto;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private CategoryDao categoryDao;

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

    @PutMapping("/activate/customer")
    public String activateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.activateCustomer(id, webRequest);
    }

    @PutMapping("/deactivate/customer")
    public String deactivateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.deactivateCustomer(id, webRequest);
    }

    @PutMapping("/activate/seller")
    public String activateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.activateSeller(id, webRequest);
    }

    @PutMapping("/deactivate/seller")
    public String deactivateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminDao.deactivateSeller(id, webRequest);
    }

    // Admin Controller for Category

    @PostMapping("/addMetadataField")
    public String addMetadataField(@RequestParam("FieldName") String fieldName){
        return categoryDao.addMetadataField(fieldName);
    }

    @GetMapping("/metadataFields")
    public List<CategoryMetadataField> getAllCategoryMetadataFields(@RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(defaultValue = "0") Integer offset,
                                                                    @RequestParam(defaultValue = "id") String order,
                                                                    @RequestParam(defaultValue = "asc") String field){
        return categoryDao.getAllMetadataFields(size, offset, order, field);
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("CategoryName") String categoryName,
                              @RequestParam(defaultValue = "")Long parentId){
        return categoryDao.addCategory(categoryName, parentId);
    }

    @GetMapping("/category")
    public SingleCategoryDto getSingleCategory(@RequestParam("CategoryId")Long id){
        return categoryDao.getSingleCategory(id);
    }

    @GetMapping("/allCategory")
    public List<SingleCategoryDto> getAllCategory(@RequestParam(defaultValue = "10") Integer size,
                                                  @RequestParam(defaultValue = "0") Integer offset,
                                                  @RequestParam(defaultValue = "id") String field,
                                                  @RequestParam(defaultValue = "asc") String order){
        return categoryDao.getAllCategory(size, offset, field, order);
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam("CategoryId") Long id, @RequestParam("CategoryName") String name){
        return categoryDao.updateCategory(id, name);
    }

    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto){
        return categoryDao.addMetadataFieldValue(categoryMetadataFieldValueDto);
    }

    @PutMapping("/updateCategoryMetadataField")
    public String updateMetadataField(@RequestBody CategoryMetadataFieldValueDto categoryMetadataFieldValueDto){
        return categoryDao.updateMetadataField(categoryMetadataFieldValueDto);
    }



}
