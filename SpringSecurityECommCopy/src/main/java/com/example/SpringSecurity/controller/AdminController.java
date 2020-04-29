package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.AdminService;
import com.example.SpringSecurity.service.CategoryService;
import com.example.SpringSecurity.service.ProductService;
import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/customers")
    public List<FindAllCustomerDTO> getCustomersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "0") Integer pageOffset,
                                                     @RequestParam(defaultValue = "id") String sortByField){
        return adminService.getCustomersList(pageSize, pageOffset,sortByField);
    }


    @GetMapping("/sellers")
    public List<FindAllSellerDTO> getSellersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "0") Integer pageOffset,
                                                 @RequestParam(defaultValue = "id") String sortByField){
        return adminService.getSellersList(pageSize, pageOffset, sortByField);
    }

    @PutMapping("/activate/customer")
    public String activateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.activateCustomer(id, webRequest);
    }

    @PutMapping("/deactivate/customer")
    public String deactivateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.deactivateCustomer(id, webRequest);
    }

    @PutMapping("/activate/seller")
    public String activateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.activateSeller(id, webRequest);
    }

    @PutMapping("/deactivate/seller")
    public String deactivateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.deactivateSeller(id, webRequest);
    }

    // Admin Controller for Category

    @PostMapping("/addMetadataField")
    public String addMetadataField(@RequestParam("FieldName") String fieldName){
        return categoryService.addMetadataField(fieldName);
    }

    @GetMapping("/metadataFields")
    public List<CategoryMetadataField> getAllCategoryMetadataFields(@RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(defaultValue = "0") Integer offset,
                                                                    @RequestParam(defaultValue = "id") String order,
                                                                    @RequestParam(defaultValue = "asc") String field){
        return categoryService.getAllMetadataFields(size, offset, order, field);
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("CategoryName") String categoryName,
                              @RequestParam(defaultValue = "")Long parentId){
        return categoryService.addCategory(categoryName, parentId);
    }

    @GetMapping("/category")
    public SingleCategoryDTO getSingleCategory(@RequestParam("CategoryId")Long id){
        return categoryService.getSingleCategory(id);
    }

    @GetMapping("/allCategory")
    public List<SingleCategoryDTO> getAllCategory(@RequestParam(defaultValue = "10") Integer size,
                                                  @RequestParam(defaultValue = "0") Integer offset,
                                                  @RequestParam(defaultValue = "id") String field,
                                                  @RequestParam(defaultValue = "asc") String order){
        return categoryService.getAllCategory(size, offset, field, order);
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam("CategoryId") Long id, @RequestParam("CategoryName") String name){
        return categoryService.updateCategory(id, name);
    }

    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryService.addMetadataFieldValue(categoryMetadataFieldValueDto);
    }

    @PutMapping("/updateCategoryMetadataField")
    public String updateMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryService.updateMetadataField(categoryMetadataFieldValueDto);
    }

    @GetMapping("/viewProduct")
    public ViewCustomerProductDTO viewProductForCustomer(@RequestParam("productId") Long productId, WebRequest webRequest){
        return productService.viewAdminProduct(productId, webRequest);
    }

    @GetMapping("/viewAllProduct")
    private List<ViewCustomerAllProductDTO> viewAllProduct(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order
    ){
        return productService.viewAllProduct(offset, max, field, order);
    }

    @PutMapping("/activateProduct")
    public String activateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productService.activateProduct(id, webRequest);
    }

    @PutMapping("/deactivateProduct")
    public String deactivateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productService.deactivateProduct(id, webRequest);
    }



}
