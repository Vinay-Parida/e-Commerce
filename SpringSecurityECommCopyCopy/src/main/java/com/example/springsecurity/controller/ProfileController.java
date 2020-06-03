package com.example.springsecurity.controller;

import com.example.springsecurity.configuration.BasicConfiguration;
import com.example.springsecurity.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private BasicConfiguration basicConfiguration;

    @GetMapping("/profile")
    public String profile(){
        return profileService.getMessage();
    }

    @GetMapping("/profileInfo")
    public BasicConfiguration getProfile(){
        BasicConfiguration newProfile = new BasicConfiguration();
        newProfile.setMessage(basicConfiguration.getMessage());
        newProfile.setNumber(basicConfiguration.getNumber());
        newProfile.setValue(basicConfiguration.isValue());
        return newProfile;
    }
}
