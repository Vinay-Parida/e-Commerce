package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.CustomerRegisterDTO;
import com.example.SpringSecurity.dto.SellerRegisterDTO;
import com.example.SpringSecurity.service.CustomerService;
import com.example.SpringSecurity.service.SellerService;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@Api(tags = {SwaggerConfig.RESISTER_TAG})
@RequestMapping(value = "/register")
public class RegistrationController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @Autowired
    MongoTemplate mongoTemplate;

    @ApiOperation(value = "Customer can register oneself")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An async email is triggered to the customer containing the link with a unique activation token to activate their account"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Validation errors")
    })
    @PostMapping("/customer")
    public String customerRegister(@Valid @RequestBody CustomerRegisterDTO customerDto, WebRequest webRequest){
        return customerService.registerCustomer(customerDto, webRequest);
    }

    @ApiOperation(value = "Seller can register oneself")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An async email is triggered to the seller specifying account has been created, waiting for approval"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "Validation errors")
    })
    @PostMapping("/seller")
    public String sellerRegister(@Valid @RequestBody SellerRegisterDTO sellerDto, WebRequest webRequest){
        return sellerService.registerSeller(sellerDto, webRequest);
    }
}
