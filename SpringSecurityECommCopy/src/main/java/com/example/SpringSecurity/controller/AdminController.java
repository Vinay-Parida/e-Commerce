package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import com.example.SpringSecurity.service.AdminService;
import com.example.SpringSecurity.service.CategoryService;
import com.example.SpringSecurity.service.ProductService;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@Api(tags = {SwaggerConfig.ADMIN_TAG})
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

        @ApiOperation(value = "Admin can view the list of all the registered customer")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Successful"),
                @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
        })
    @GetMapping("/customers")
    public List<FindAllCustomerDTO> getCustomersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "0") Integer pageOffset,
                                                     @RequestParam(defaultValue = "id") String sortByField){
        return adminService.getCustomersList(pageSize, pageOffset,sortByField);
    }

    @ApiOperation(value = "Admin can view the list of all the registered seller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/sellers")
    public List<FindAllSellerDTO> getSellersList(@RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "0") Integer pageOffset,
                                                 @RequestParam(defaultValue = "id") String sortByField){
        return adminService.getSellersList(pageSize, pageOffset, sortByField);
    }

    @ApiOperation(value = "Admin can activate a registered Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer Account activated successfully"),
            @ApiResponse(code = 400, message = "Required Long parameter 'UserId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Customer Does Not Exists")
    })
    @PutMapping("/activate/customer")
    public String activateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.activateCustomer(id, webRequest);
    }

    @ApiOperation(value = "Admin can deactivate a registered customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seller Account Deactivated Successfully"),
            @ApiResponse(code = 400, message = "Required Long parameter 'UserId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Seller Does Not Exists")
    })
    @PutMapping("/deactivate/customer")
    public String deactivateCustomer(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.deactivateCustomer(id, webRequest);
    }

    @ApiOperation(value = "Admin can activate a registered seller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seller Account activated Successfully"),
            @ApiResponse(code = 400, message = "Required Long parameter 'UserId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Seller Does Not Exists")
    })
    @PutMapping("/activate/seller")
    public String activateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.activateSeller(id, webRequest);
    }

    @ApiOperation(value = "Admin can deactivate a registered seller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seller Account deactivated Successfully"),
            @ApiResponse(code = 400, message = "Required Long parameter 'UserId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Seller Does Not Exists")
    })
    @PutMapping("/deactivate/seller")
    public String deactivateSeller(@RequestParam("UserId") Long id, WebRequest webRequest){
        return adminService.deactivateSeller(id, webRequest);
    }

    // Admin Controller for Category

    @ApiOperation(value = "Admin can create a metadata fields for category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message: Field saved with a field id"),
            @ApiResponse(code = 400, message = "Required String parameter 'FieldName' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Field Name can't be null")
    })
    @PostMapping("/addMetadataField")
    public String addMetadataField(@RequestParam("FieldName") String fieldName){
        return categoryService.addMetadataField(fieldName);
    }

    @ApiOperation(value = "Admin can see all metadata fields")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all metadata with their value list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/metadataFields")
    public List<CategoryMetadataField> getAllCategoryMetadataFields(@RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(defaultValue = "0") Integer offset,
                                                                    @RequestParam(defaultValue = "id") String order,
                                                                    @RequestParam(defaultValue = "asc") String field){
        return categoryService.getAllMetadataFields(size, offset, order, field);
    }

    @ApiOperation(value = "Admin can create a category with a parentId and Category with parentId null is a root category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message with a category id"),
            @ApiResponse(code = 400, message = "Required String parameter 'CategoryName' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Field Name can't be null")
    })
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("CategoryName") String categoryName,
                              @RequestParam(defaultValue = "")Long parentId){
        return categoryService.addCategory(categoryName, parentId);
    }

    @ApiOperation(value = "Admin can view a single category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category details with parent categories up to root level and immediate children categories, and associated fields along with possible values"),
            @ApiResponse(code = 400, message = "Required String parameter 'CategoryName' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Field Name can't be null")
    })
    @GetMapping("/category")
    public SingleCategoryDTO getSingleCategory(@RequestParam("CategoryId")Long id){
        return categoryService.getSingleCategory(id);
    }

    @ApiOperation(value = "Admin can view list of all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categories list with each individual category's details as well"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Field Name can't be null")
    })
    @GetMapping("/allCategory")
    public List<SingleCategoryDTO> getAllCategory(@RequestParam(defaultValue = "10") Integer size,
                                                  @RequestParam(defaultValue = "0") Integer offset,
                                                  @RequestParam(defaultValue = "id") String field,
                                                  @RequestParam(defaultValue = "asc") String order){
        return categoryService.getAllCategory(size, offset, field, order);
    }

    @ApiOperation(value = "Admin can update a category name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 400, message = "Required String parameters are not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Category name already exists")
    })
    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam("CategoryId") Long id, @RequestParam("CategoryName") String name){
        return categoryService.updateCategory(id, name);
    }

    @ApiOperation(value = "Admin can add new category metadata field for a category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Category metadata field or category metadata field value or category already exists")
    })
    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryService.addMetadataFieldValue(categoryMetadataFieldValueDto);
    }

    @ApiOperation(value = "Admin can update values for an existing metadata field in a category but previously saved values for a categoryId and metadata field Id are not deleted or updated")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "Category metadata field or category metadata field value or category already exists")
    })
    @PutMapping("/updateCategoryMetadataField")
    public String updateMetadataField(@RequestBody CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto){
        return categoryService.updateMetadataField(categoryMetadataFieldValueDto);
    }

    @ApiOperation(value = "Admin can view a single product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product details along with product's selected category details, all variations primary images"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "ProductId can't be null")
    })
    @GetMapping("/viewProduct")
    public ViewCustomerProductDTO viewProductForCustomer(@RequestParam("productId") Long productId, WebRequest webRequest){
        return productService.viewAdminProduct(productId, webRequest);
    }

    @ApiOperation(value = "Admin can view all single products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all products, along with each product's category details, all variations primary images."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
    })
    @GetMapping("/viewAllProduct")
    private List<ViewCustomerAllProductDTO> viewAllProduct(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order
    ){
        return productService.viewAllProduct(offset, max, field, order);
    }

    @ApiOperation(value = "Admin can activate a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message and an email is being sent to the product owner informing activation and other product details"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "ProductId can't be null")
    })
    @PutMapping("/activateProduct")
    public String activateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productService.activateProduct(id, webRequest);
    }

    @ApiOperation(value = "Admin can deactivate a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message and an email is being sent to the product owner informing deactivation and other product details"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Required parameters doesn't exists"),
            @ApiResponse(code = 409, message = "ProductId can't be null")
    })
    @PutMapping("/deactivateProduct")
    public String deactivateProduct(@RequestParam("id") Long id, WebRequest webRequest){
        return productService.deactivateProduct(id, webRequest);
    }



}
