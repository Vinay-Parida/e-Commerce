package com.example.springsecurity.controller;

import com.example.springsecurity.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadImageController {

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/uploadImage")
        public String uploadImage(@RequestBody MultipartFile image, HttpServletRequest httpServletRequest) throws IOException {
            return uploadImageService.uploadImage(image, httpServletRequest);
    }
}
