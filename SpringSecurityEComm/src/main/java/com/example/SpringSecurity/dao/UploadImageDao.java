package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Component
public class UploadImageDao {

    @Autowired
    UserRepository userRepository;

    public String uploadImage(MultipartFile image, HttpServletRequest httpServletRequest) throws IOException {

        if (image.isEmpty()) {
            return "Please Upload an Image";
        }
        try {
            byte[] bytes = image.getBytes();
            User user = getUser(httpServletRequest);
            String fileName = renameFile(image.getOriginalFilename(), user);

            Path path = Paths.get("/home/vinay/images/" + fileName);
            Files.write(path, bytes);

            saveFilePath("/home/vinay/images/" + fileName, user);
            return "Photo successfully uploaded";

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void saveFilePath(String filePath, User user) {
        user.setImage(filePath);
        userRepository.save(user);
    }

    private String renameFile(String fileName, User user) {
        Integer index = fileName.lastIndexOf(".");
        fileName = fileName.substring(index);
        Long id = user.getId();
        return id + fileName;
    }

    private User getUser(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        return user;
    }

}
