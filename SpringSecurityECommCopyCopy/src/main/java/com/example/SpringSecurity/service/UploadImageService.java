package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.ProductException;
import com.example.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UploadImageService {

    @Autowired
    UserRepository userRepository;

    public String uploadImage(MultipartFile image, HttpServletRequest httpServletRequest) throws IOException {

        if (image.isEmpty()) {
            return "Please Upload an Image";
        }
        try {
            byte[] bytes = image.getBytes();
            User user = getUser(httpServletRequest);
            String fileName = renameFile(image.getOriginalFilename(), user.getId());

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

    private String renameFile(String fileName, Long id) {
        Integer index = fileName.lastIndexOf(".");
        fileName = fileName.substring(index);
        if (fileName.equalsIgnoreCase(".jpg") || fileName.equalsIgnoreCase(".jpeg") || fileName.equalsIgnoreCase(".png") || fileName.equalsIgnoreCase(".bmp"))
            return id + fileName;
        else
            throw new ProductException("Image format is not valid");
    }

    private User getUser(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        return user;
    }


    public String uploadPrimaryImage(MultipartFile image, Long id, WebRequest webRequest) throws IOException {
        if (image.isEmpty())
            return "No file Selected";
        
        byte[] bytes = image.getBytes();
        String fileName = renameFile(image.getOriginalFilename(), id);
        Path path = Paths.get("/home/vinay/images/productVariation/primaryImage" + fileName);
        Files.write(path, bytes);
        return "/home/vinay/images/productVariation/primaryImage" + fileName;
    }

    public Set<String> uploadSecondaryImage(List<MultipartFile> images , WebRequest webRequest, Long productId) throws IOException {

        Integer count=0;
        Set<String> pathNames=new HashSet<>();
        for (MultipartFile image:images) {
            count++;
            String folder = "/home/vinay/images/productVariation/secondaryImage";
            byte[] bytes = image.getBytes();
            String fileName=getSecondaryFileName(image.getOriginalFilename(),productId,count);
            Path path = Paths.get(folder + fileName);
            Files.write(path, bytes);
            pathNames.add(folder+fileName);
        }
        return pathNames;
    }
    private String getSecondaryFileName(String fileName,Long id,Integer count){
        Integer l=fileName.lastIndexOf(".");
        fileName=fileName.substring(l);
        if(fileName.equalsIgnoreCase(".jpg") || fileName.equalsIgnoreCase(".jpeg") ||
                fileName.equalsIgnoreCase(".png") || fileName.equalsIgnoreCase(".bmp"))
            return  (id+"."+count+fileName);
        else
            throw new ProductException("Image format is not valid!");
    }

}
