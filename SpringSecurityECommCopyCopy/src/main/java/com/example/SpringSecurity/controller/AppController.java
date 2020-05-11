package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class AppController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String name = "";
        String authHeader = request.getHeader("Authorization");
        if(authHeader!= null){
            String tokenValue = authHeader.replace("Bearer","").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            Principal principal = request.getUserPrincipal();
            name = principal.getName();
        }
        return name + " is Logged out successfully";
    }

//    @GetMapping("/login")
//    public String index(ModelMap modelMap){
//        return "login";
//    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "Admin Home";
    }

    @GetMapping("/user/home")
    public String userHome(){
        return "User Home";
    }

}

