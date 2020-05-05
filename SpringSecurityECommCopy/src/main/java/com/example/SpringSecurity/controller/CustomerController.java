package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.CategoryService;
import com.example.SpringSecurity.service.CustomerService;
import com.example.SpringSecurity.service.ProductService;
import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = {SwaggerConfig.CUSTOMER_TAG})
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "Customer can view his profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/profile")
    public CustomerProfileDTO viewProfile(HttpServletRequest httpServletRequest){
        return customerService.getProfile(httpServletRequest);
    }

    @ApiOperation(value = "Customer can add a new address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressDTO addressDto, HttpServletRequest httpServletRequest){
        return customerService.addAddress(addressDto, httpServletRequest);
    }

    @ApiOperation(value = "Customer can view all his addresses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of addresses"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/address")
    public List<AddressDTO> getAddressList(HttpServletRequest httpServletRequest){
        return customerService.getAddressList(httpServletRequest);
    }

    @ApiOperation(value = "Customer update his profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping("/updateProfile")
    public String updateProfile(@Valid @RequestBody HashMap<String, Object> stringObjectHashMap, HttpServletRequest httpServletRequest){
        return customerService.updateProfile(stringObjectHashMap,httpServletRequest);
    }

    @ApiOperation(value = "Customer update his password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful message"),
            @ApiResponse(code = 400, message = "Required String parameters are not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Password and Confirm password doesn't match")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, HttpServletRequest httpServletRequest){
        return customerService.updatePassword(password, confirmPassword,httpServletRequest);
    }

    @ApiOperation(value = "Customer delete his existing address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful message"),
            @ApiResponse(code = 400, message = "Required String parameter 'Id' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Address not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/deleteAddress")
    public String deleteAddress(@RequestParam("Id") Long id, HttpServletRequest httpServletRequest){
        return customerService.deleteAddress(id, httpServletRequest);
    }

    @ApiOperation(value = "Customer update his existing address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful message"),
            @ApiResponse(code = 400, message = "Required String parameter 'addressId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Address not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping("/updateAddress")
    public String updateAddress(@RequestParam("addressId") Long id, @RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest) throws Exception {
        return customerService.updateAddress(map, id, httpServletRequest);
    }

    @ApiOperation(value = "Customer can see the list of all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of valid categories at same level and with same parent"),
            @ApiResponse(code = 400, message = "Required String parameter 'CategoryId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/getAllCategories")
    public List<CategoryDTO> getAllCategory(@RequestParam("CategoryId")Long id){
        return categoryService.getAllCategoryForCustomer(id);
    }

    @ApiOperation(value = "Customer can filter products with same category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of valid categories at same level and with same parent"),
            @ApiResponse(code = 400, message = "Required String parameter 'CategoryId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "CategoryId not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/filterCategory")
    public CategoryFilterDTO filterCategory(@RequestParam("CategoryId") Long categoryId, WebRequest webRequest){
        return categoryService.getFilterData(categoryId,webRequest);
    }

    @ApiOperation(value = "Customer view a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product details along with product's selected category details and all variations details including images"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Product exception")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewProduct")
    public ViewCustomerProductDTO viewProductForCustomer(@RequestParam("productId") Long productId, WebRequest webRequest){
        return productService.viewCustomerProduct(productId, webRequest);
    }

    @ApiOperation(value = "Customer can view all product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all products, along with each product's category details, all variations primary images"),
            @ApiResponse(code = 400, message = "Required String parameter 'CategoryId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Category is not leaf node")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewAllProduct")
    public List<ViewCustomerAllProductDTO> viewAllProductForCustomer(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order,
            @RequestParam("categoryId") Long categoryId,
            WebRequest webRequest
    ){
        return productService.viewCustomerAllProduct(categoryId,offset,webRequest,max,field,order);
    }

    @ApiOperation(value = "Customer can view similar product which have same category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The products fetched are similar to current product."),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "productId doesn't exist")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping(value = "/viewSimilarProduct")
    public List<ViewCustomerAllProductDTO> viewSimilarProduct(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String max,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue= "Ascending") String order,
            @RequestParam("productId") Long productId,
            WebRequest webRequest
    ){
        return productService.viewSimilarProduct(productId,webRequest,offset,max,field,order);
    }



}
