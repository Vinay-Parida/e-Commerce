package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.dto.AddProductDto;
import com.example.SpringSecurity.entity.products.Category;
import com.example.SpringSecurity.entity.products.Product;
import com.example.SpringSecurity.entity.users.Seller;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.CategoryException;
import com.example.SpringSecurity.exceptions.ProductException;
import com.example.SpringSecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class ProductDao {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryMetadataFieldValueRepository categoryMetadataFieldValueRepository;

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private UploadImageDao uploadImageDao;

    public String addProduct(AddProductDto addProductDto, HttpServletRequest httpServletRequest, WebRequest webRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long userId = userRepository.findByEmail(email).getId();
        Seller seller = sellerRepository.findById(userId);
        Category category = categoryRepository.findById(addProductDto.getCategoryId()).get();
        Product product = new Product();
        product.setSeller(seller);
        product.setBrand(addProductDto.getBrand());
        Boolean productUnique = isProductUnique(addProductDto.getName(), addProductDto.getCategoryId(), seller.getId(), addProductDto.getBrand());
        if (productUnique)
            product.setName(addProductDto.getName());
        else
            throw new ProductException("Product with this name is already exist.");

        if (categoryRepository.findByParentId(addProductDto.getCategoryId()).isEmpty() && category != null)
            product.setCategory(category);
        else
            throw new CategoryException("Invalid category id");

        if (addProductDto.getDescription() != null)
            product.setDescription(addProductDto.getDescription());
        if (addProductDto.getCancellable() != null) product.setSeller(seller);
        product.setBrand(addProductDto.getBrand());
        product.setCancellable(addProductDto.getCancellable());
        if (addProductDto.getReturnable() != null)
            product.setReturnable(addProductDto.getReturnable());
        productRepository.save(product);
        Long productId = productRepository.getUniqueProduct(product.getBrand(), product.getCategory().getId(), product.getSeller().getId(), product.getName()).getId();
        List<User> userAdmin = userRepository.getUserAdmin();
        String confirmationUrl = webRequest.getContextPath() + "/admin/activateProduct?id=" + productId;
        userAdmin.forEach(user -> {
            sendMailToAdmin(user.getEmail(), confirmationUrl, product);
        });

        return "Product Saved Successfully";
    }

    private void sendMailToAdmin(String emailRecipient, String confirmationUrl, Product product) {
        String message = getMailBody(product);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRecipient);
        simpleMailMessage.setSubject("Product Activation Confirmation");
        simpleMailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        javaMailSender.send(simpleMailMessage);
    }

    private String getMailBody(Product product) {
        String mailBody =
                "Seller Name = " + product.getSeller().getCompany_name() + "\r\n" +
                        "Product Name = " + product.getName() + "\r\n" +
                        "Description = " + product.getDescription() + "\r\n" +
                        "IsCancellable = " + product.getCancellable() + "\r\n" +
                        "IsReturnable = " + product.getReturnable() + "\r\n" +
                        "Brand = " + product.getBrand() + "\r\n" +
                        "IsActive = " + product.getActive() + "\r\n" +
                        "Category = " + product.getCategory().getName();
        return mailBody;
    }

    private Boolean isProductUnique(String name, Long categoryId, Long sellerId, String brand) {
        Product product = productRepository.getUniqueProduct(brand, categoryId, sellerId, name);
        if (product == null)
            return true;
        else
            return false;
    }


//    public String addProductVariation(MultipartFile primaryImage, List<MultipartFile> secondaryImages, AddProductVariationDto addProductVariationDto, HttpServletRequest httpServletRequest, WebRequest webRequest) throws IOException {
//        String sellerEmail = httpServletRequest.getUserPrincipal().getName();
//        String result = validateMetadata(addProductVariationDto, sellerEmail);
//        if (result.equalsIgnoreCase("Success")) {
//            ProductVariation productVariation = new ProductVariation();
//            productVariation.setProduct(productRepository.findById(addProductVariationDto.getProductId()).get());
//            productVariation.setPrice(addProductVariationDto.getPrice());
//            productVariation.setQuantityAvailable(addProductVariationDto.getQuantityAvailable());
//
//
//            String primaryImagePath = uploadImageDao.
//
//        }
//    }
//
//    private String validateMetadata(AddProductVariationDto addProductVariationDto, String sellerEmail) {
//        String message;
//        System.out.println("Before product-------");
//        Product product = productRepository.findById(addProductVariationDto.getProductId()).get();
//        System.out.println("After product-------");
//        if (product == null) {
//            message = "Product Id is invalid";
//            return message;
//        }
//
//        Integer quantity = addProductVariationDto.getQuantityAvailable();
//        if (quantity != null) {
//            if (quantity < 0) {
//                message = "Product quantity must not be negative";
//                return message;
//            }
//        }
//        Double price = addProductVariationDto.getPrice();
//        if (price < 0) {
//            message = "Product price must not be negative";
//            return message;
//        }
//        if (product.getDeleted()) {
//            message = "Product is deleted";
//            return message;
//        }
//        if (!product.getActive()) {
//            message = "Product is inactive";
//            return message;
//        }
//        Category category = product.getCategory();
//        Map<String, String> metadataMap = addProductVariationDto.getMetadata();
//
//    }


    public String activateProduct(Long productId, WebRequest webRequest){
        Product product = productRepository.findById(productId).get();
        product.setActive(true);
        productRepository.save(product);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String sellerEmail = product.getSeller().getEmail();
        String subject = "Product Activation";
        String message = "Your product " + product.getName() + ": " + product.getBrand() + " is activated by admin";

        simpleMailMessage.setTo(sellerEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);

        return "Product activated successfully";
    }

    public String deactivateProduct(Long productId, WebRequest webRequest){
        Product product = productRepository.findById(productId).get();
        product.setActive(false);
        productRepository.save(product);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String sellerEmail = product.getSeller().getEmail();
        String subject = "Product Deactivation";
        String message = "Your product " + product.getName() + ": " + product.getBrand() + " is deactivated by admin";

        simpleMailMessage.setTo(sellerEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);

        return "Product deactivated successfully";
    }


}
