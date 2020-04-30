package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.ActivationService;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@Api(tags = {SwaggerConfig.ACTIVATION_TAG})
public class ActivationController {

    @Autowired
    private ActivationService activationService;

    @ApiOperation(value = "Customer can activate his account after registration", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 201, message = "Customer Account activated Successfully"),
            @ApiResponse(code = 400, message = "Required String parameter 'token' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest webRequest, @RequestParam("token") String token){
        return activationService.activateUser(token, webRequest);
    }

    @ApiOperation(value = "Customer get the link to re-send activation link to activate his account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 201, message = "Customer received re activation link to activate his account in email"),
            @ApiResponse(code = 400, message = "Required String parameter 'email' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/reactivateUser")
    public String reactivateUser(WebRequest webRequest, @RequestParam("email") String email){
        return activationService.reactivationUser(email,webRequest);
    }
}
