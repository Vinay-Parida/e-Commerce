package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.service.ForgetPasswordService;
import com.example.SpringSecurity.swagger.SwaggerConfig;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Api(tags = {SwaggerConfig.FORGET_PASSWORD_TAG})
public class ForgotPasswordController {

    @Autowired
    ForgetPasswordService forgetPasswordService;

    @Autowired
    MessageSource messageSource;

    @ApiOperation(value = "User can Forget his password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A unique token is generated to create a new password and sent to user's email"),
            @ApiResponse(code = 400, message = "Required String parameter 'email' is not present"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping(value = "/forgetPassword")
    public String forgetPassword(@Valid @Param("email") String email, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        Boolean isEmailValid = matcher.matches();

        if (isEmailValid)
            return forgetPasswordService.sendForgetPasswordToken(email, webRequest);
        else
            throw new EmailException(messageSource.getMessage("exception.invalid.email", null, locale));
    }

    @ApiOperation(value = "User reset his password via unique token generated")
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
    @PutMapping(value = "/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword, WebRequest webRequest){
        return forgetPasswordService.resetPassword(token, password, confirmPassword,webRequest);
    }
}
