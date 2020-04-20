package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.AdminDAO;
import com.example.SpringSecurity.dao.CategoryDAO;
import com.example.SpringSecurity.dao.ProductDAO;
import com.example.SpringSecurity.dto.CategoryMetadataFieldValueDTO;
import com.example.SpringSecurity.dto.FindAllCustomerDTO;
import com.example.SpringSecurity.dto.FindAllSellerDTO;
import com.example.SpringSecurity.dto.SingleCategoryDTO;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDAO adminDao;

    @Autowired
    private CategoryDAO categoryDao;

    @Autowired
    private ProductDAO productDao;

    @GetMapping("/customers")
    public List<FindAllCustomerDTO> getCustomersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "0") Integer pageOffset,
                                                     @RequestParam(defaultValue = "id") String sortByField){
        return adminDao.getCustomersList(pageSize, pageOffset,sortByField);
    }


    @GetMapping("/sellers")
    public List<FindAllSellerDTO> getSellersList(@RequestParam(defaultValue = "10") Integer pageSize,
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
    public SingleCategoryDTO getSingleCategory(@RequestParam("CategoryId")Long id){
        return categoryDao.getSingleCategory(id);
    }

    @GetMapping("/allCategory")
    public List<SingleCategoryDTO> getAllCategory(@RequestParam(defaultValue = "10") Integer size,
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
    public String addCategoryMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryDao.addMetadataFieldValue(categoryMetadataFieldValueDto);
    }

    @PutMapping("/updateCategoryMetadataField")
    public String updateMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryDao.updateMetadataField(categoryMetadataFieldValueDto);
    }

    @PutMapping("/activateProduct")
    public String activateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productDao.activateProduct(id, webRequest);
    }

    @PutMapping("/deactivateProduct")
    public String deactivateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productDao.deactivateProduct(id, webRequest);
    }



}
