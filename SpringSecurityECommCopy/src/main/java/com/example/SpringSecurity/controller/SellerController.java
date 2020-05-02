package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.service.CategoryService;
import com.example.SpringSecurity.service.CustomerService;
import com.example.SpringSecurity.service.ProductService;
import com.example.SpringSecurity.service.SellerService;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.*;
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
@Api(tags = {SwaggerConfig.SELLER_TAG})
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Seller can view his profile")
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
    public SellerProfileDTO getSellerProfile(HttpServletRequest httpServletRequest) {
        return sellerService.getSellerProfile(httpServletRequest);
    }

    @ApiOperation(value = "Seller can update his password")
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
    public String updatePassword(@Param("password") String password, @Param("confirmPassword") String confirmPassword, HttpServletRequest httpServletRequest) {
        return customerService.updatePassword(password, confirmPassword, httpServletRequest);
    }

    @ApiOperation(value = "Seller can update his existing address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping("/updateAddress")
    public String updateAddress(@RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest) throws Exception {
        return sellerService.updateSellerAddress(map, httpServletRequest);
    }

    @ApiOperation(value = "Seller can update his profile")
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
    public String updateProfile(@Valid @RequestBody HashMap<String, Object> map, HttpServletRequest httpServletRequest) {
        return sellerService.updateProfile(map, httpServletRequest);
    }

    @ApiOperation(value = "Seller can view all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all leaf node categories along with their metadata fields, field's possible values and parent node chain details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/getAllCategories")
    public List<CategoryForSellerDTO> getAllCategories() {
        return categoryService.getCategoryForSeller();
    }

    @ApiOperation(value = "Seller can add a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping("/addProduct")
    public String addProduct(@RequestBody AddProductDTO addProductDto, HttpServletRequest httpServletRequest, WebRequest webRequest) {
        return productService.addProduct(addProductDto, httpServletRequest, webRequest);
    }

    @ApiOperation(value = "Seller can add a product variation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 400, message = "Required String parameters are not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping(value = "/addProductVariation", consumes = {"multipart/form-data"})
    public String addProductVariation(@RequestPart("primaryImage") MultipartFile primaryimage,
                                      @RequestPart("secondaryImages") List<MultipartFile> secondaryImages,
                                      HttpServletRequest httpServletRequest,
                                      @RequestPart("productVariation") AddProductVariationDTO addProductVariationDTO,
                                      WebRequest webRequest) throws IOException {
        return productService.addProductVariation(primaryimage, secondaryImages, httpServletRequest, addProductVariationDTO, webRequest);
    }

    @ApiOperation(value = "Seller can view a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product details along with selected category details"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewProduct")
    private ViewProductDTO viewProduct(@RequestParam("productId") Long productId, WebRequest webRequest, HttpServletRequest request) {
        return productService.viewProduct(productId, webRequest, request);
    }

    @ApiOperation(value = "Seller can view a product variation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product variation details with parent product details"),
            @ApiResponse(code = 400, message = "Required String parameter 'variationId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewProductVariation")
    private ViewProductVariationDTO viewProductVariation(@RequestParam("variationId") Long variationId, WebRequest webRequest, HttpServletRequest request) {
        return productService.viewProductVariation(variationId, webRequest, request);
    }

    @ApiOperation(value = "Seller can view all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all products created by the logged in user, along with each product's category details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewAllProduct")
    private List<ViewProductDTO> viewAllProduct(@RequestParam(defaultValue = "0") String offset,
                                                @RequestParam(defaultValue = "10") String max,
                                                @RequestParam(defaultValue = "id") String field,
                                                @RequestParam(defaultValue = "Ascending") String order, HttpServletRequest request) {
        return productService.viewAllProduct(offset, max, field, order, request);
    }

    @ApiOperation(value = "Seller can view all product variations of a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all product variations of a product"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/viewAllProductVariation")
    private List<ViewAllProductVariationDTO> productVariationList(@RequestParam("productId") Long productId,
                                                                  @RequestParam(defaultValue = "0") String offset,
                                                                  @RequestParam(defaultValue = "10") String max,
                                                                  @RequestParam(defaultValue = "id") String field,
                                                                  @RequestParam(defaultValue= "asc") String order,
                                                                  HttpServletRequest request,WebRequest webRequest){
        return productService.viewAllProductVariation(productId, offset, max, field, order, request, webRequest);
    }

    @ApiOperation(value = "Seller can delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/deleteProduct")
    private String deleteProduct(@RequestParam("productId")Long productId,HttpServletRequest request,WebRequest webRequest){
        return productService.deleteProduct(productId, request, webRequest);
    }

    @ApiOperation(value = "Seller can update a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 400, message = "Required String parameter 'productId' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping("/updateProduct")
    private String updateProduct(@RequestBody UpdateProductDTO updateProductDto,HttpServletRequest request,WebRequest webRequest){
        return productService.updateProduct(updateProductDto,request,webRequest);
    }

    @ApiOperation(value = "Seller can update product variation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success message"),
            @ApiResponse(code = 400, message = "Required String parameters are not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Product Validation errors")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PutMapping(value = "/updateProductVariation",consumes ={"multipart/form-data"})
    private String updateProductVariation(@RequestPart("primaryImage") MultipartFile primaryImage,
                                          @RequestPart("secondaryImage") List<MultipartFile> secondaryImage,
                                          @RequestPart("updateProductVariationDto") UpdateProductVariationDTO updateProductVariationDto,
                                          WebRequest webRequest,HttpServletRequest request ) throws IOException {
        return productService.updateProductVariation(primaryImage,secondaryImage,request,updateProductVariationDto,webRequest);
    }

}
