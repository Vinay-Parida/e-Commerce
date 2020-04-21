package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.entity.products.Category;
import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import com.example.SpringSecurity.entity.products.Product;
import com.example.SpringSecurity.entity.products.ProductVariation;
import com.example.SpringSecurity.entity.users.Seller;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.CategoryException;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.ProductException;
import com.example.SpringSecurity.repository.*;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Component
public class ProductDAO {
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
    private UploadImageDAO uploadImageDao;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public String addProduct(AddProductDTO addProductDto, HttpServletRequest httpServletRequest, WebRequest webRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long userId = userRepository.findByEmail(email).getId();
        Seller seller = sellerRepository.findById(userId);
        Category category = categoryRepository.findById(addProductDto.getCategoryId()).get();
        Product product = new Product();

        Boolean productUnique = isProductUnique(addProductDto.getName(), addProductDto.getCategoryId(), seller.getId(), addProductDto.getBrand());
        if (productUnique) {
            product.setName(addProductDto.getName());
            product.setSeller(seller);
            product.setBrand(addProductDto.getBrand());
        }
        else
            throw new ProductException("Product with this name is already exist.");

        if (categoryRepository.findByParentId(addProductDto.getCategoryId()).isEmpty() && category != null)
            product.setCategory(category);
        else
            throw new CategoryException("Invalid category id");

        if (addProductDto.getDescription() != null)
            product.setDescription(addProductDto.getDescription());
        if (addProductDto.getCancellable() != null)
            product.setSeller(seller);
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
                "Seller Name = " + product.getSeller().getCompanyName() + "\r\n" +
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


    public String addProductVariation(MultipartFile primaryImage, List<MultipartFile> secondaryImages, HttpServletRequest request, AddProductVariationDTO addProductVariationDto, WebRequest webRequest) throws IOException {
        String sellerEmail = request.getUserPrincipal().getName();
        String validateData = validateData(addProductVariationDto, sellerEmail, webRequest);
        if(!validateData.equalsIgnoreCase("success"))
            throw  new EmailException("Validation Failed");

        ProductVariation productVariation = new ProductVariation();
        Product product = productRepository.findById(addProductVariationDto.getProductId()).get();
        productVariation.setPrice(addProductVariationDto.getPrice());
        productVariation.setProduct(product);
        productVariation.setPrimaryImageName(uploadImageDao.uploadPrimaryImage(primaryImage,addProductVariationDto.getProductId(), webRequest));
        productVariation.setQuantityAvailable(addProductVariationDto.getQuantityAvailable());
        productVariation.setMetadata(addProductVariationDto.getMetadata());

        if(!secondaryImages.isEmpty()){
            Set<String> strings = uploadImageDao.uploadSecondaryImage(secondaryImages, webRequest, productVariation.getProduct().getId());
            String convert = SetStringConverter.convertToString(strings);
            productVariation.setSecondaryImage(convert);
        }

        productVariationRepository.save(productVariation);
        return "Product variation Saved successfully";
    }

    private String validateData(AddProductVariationDTO addProductVariationDto,String sellerEmail,WebRequest webRequest){

        Product product = productRepository.findById(addProductVariationDto.getProductId()).get();

        if(product==null)
            throw new EmailException("Product no available");

        if(!product.getActive())
            throw new EmailException("Product is not active");

        if(!product.getSeller().getEmail().equalsIgnoreCase(sellerEmail))
            throw new EmailException("This is not product of the seller");

        if(addProductVariationDto.getQuantityAvailable()!=null){
            if(addProductVariationDto.getQuantityAvailable()<0 )
                throw new EmailException("Quantity can't be negative");
        }
        if(addProductVariationDto.getPrice()!=null) {
            if (addProductVariationDto.getPrice() < 0)
                throw new EmailException("Price can't be negative");
        }
        if(product.getDeleted()){
            throw new EmailException("Product is deleted");
        }

        Category category=product.getCategory();
        Long categoryId = category.getId();
        Map<String, String> metadata = addProductVariationDto.getMetadata();

        List<String> recievedFields =new ArrayList<>(metadata.keySet());
        List<String> actualFields= new ArrayList<>();
        List<Object> metadataFieldName = categoryMetadataFieldValueRepository.getMetadataFieldName(categoryId);
        metadataFieldName.forEach((e)->{
            actualFields.add((String)e);
        });
        if(recievedFields.size()<actualFields.size())
            throw new EmailException("Fields are less. Not as same as required fields");

        recievedFields.removeAll(actualFields);
        if(!recievedFields.isEmpty())
            throw new EmailException("Fields are more. Not as same as required fields");

        List<String> metadataFields =new ArrayList<>(metadata.keySet());
        for (String fieldName: metadataFields) {
            CategoryMetadataField field = categoryMetadataFieldRepository.findByName(fieldName);
            List<Object> valueFromCategoryAndField= categoryMetadataFieldValueRepository.getMetadataFieldValuesByCategoryAndFieldId(categoryId, field.getId());
            String values=valueFromCategoryAndField.get(0).toString();
            Set<String> dbValue= SetStringConverter.convertToSet(values);

            String receivedValues= metadata.get(fieldName);
            if(receivedValues.isEmpty())
                throw new EmailException("Values can't be empty");

            Set<String> actualSet = SetStringConverter.convertToSet(receivedValues);
            Set<String> union = Sets.union(actualSet, dbValue);
            if(union.size()>dbValue.size())
                throw new EmailException("This variation value doesn't exists");
        }
        return "Success";
    }

    public ViewProductDTO viewProduct(Long productId, WebRequest webRequest, HttpServletRequest request){
        String sellerEmail = request.getUserPrincipal().getName();

        Optional<Product> productOptional=productRepository.findById(productId);
        if(!productOptional.isPresent())
            throw new EmailException("Invalid Product Id");

        Product product = productOptional.get();
        String email = product.getSeller().getEmail();
        if(!email.equalsIgnoreCase(sellerEmail))
            throw new EmailException("Product doesn't belong to the seller");

        else if(product.getDeleted())
            throw new EmailException("Product is deleted");

        ViewProductDTO viewProductDto=new ViewProductDTO();
        viewProductDto.setProductId(product.getId());
        viewProductDto.setProductName(product.getName());
        viewProductDto.setBrand(product.getBrand());
        viewProductDto.setDescription(product.getDescription());
        viewProductDto.setCancellable(product.getCancellable());
        viewProductDto.setReturnable(product.getReturnable());
        viewProductDto.setActive(product.getActive());

        Category category = product.getCategory();
        viewProductDto.setCategoryId(category.getId());
        viewProductDto.setCategoryName(category.getName());
        List<CategoryMetadataDTO> nameAndValuesDtos=new ArrayList<>();
        List<Object[]> metadataNameAndValues = categoryRepository.getMetadataByCategoryId(category.getId());
        for (Object[] object:  metadataNameAndValues) {
            CategoryMetadataDTO categoryMetadataDTO=new CategoryMetadataDTO();
            categoryMetadataDTO.setMetadataField((String) object[0]);
            categoryMetadataDTO.setMetadataFieldValue((String)object[1]);
            nameAndValuesDtos.add(categoryMetadataDTO);
        }
        viewProductDto.setCategoryMetadataDTOS(nameAndValuesDtos);
        return viewProductDto;
    }

    public ViewProductVariationDTO viewProductVariation(Long variationId , WebRequest webRequest,HttpServletRequest request){

        String sellerEmail = request.getUserPrincipal().getName();
        Optional<ProductVariation> variationOptional = productVariationRepository.findById(variationId);
        if(!variationOptional.isPresent())
            throw new EmailException("Variation doesn't exists");

        ProductVariation productVariation = variationOptional.get();
        Product product = productVariation.getProduct();
        if(product.getDeleted())
            throw new EmailException("Product is deleted");

        String email = product.getSeller().getEmail();
        if(!email.equalsIgnoreCase(sellerEmail))
            throw new EmailException("Product doesn't belong to the seller");


        ViewProductVariationDTO viewProductVariationDto=new ViewProductVariationDTO();
        viewProductVariationDto.setProductId(product.getId());
        viewProductVariationDto.setProductName(product.getName());
        viewProductVariationDto.setProductBrand(product.getBrand());
        viewProductVariationDto.setProductDescription(product.getDescription());
        viewProductVariationDto.setPrice(productVariation.getPrice());
        viewProductVariationDto.setQuantityAvailable(productVariation.getQuantityAvailable());
        viewProductVariationDto.setVariationActive(productVariation.getActive());
        viewProductVariationDto.setCancellable(product.getCancellable());
        viewProductVariationDto.setReturnable(product.getReturnable());
        viewProductVariationDto.setPrimageImage(productVariation.getPrimaryImageName());

        if(productVariation.getSecondaryImage()!=null) {
            viewProductVariationDto.setSecondaryImage(SetStringConverter.convertToSet(productVariation.getSecondaryImage()));
        }
        viewProductVariationDto.setMetadata(productVariation.getMetadata());

        return viewProductVariationDto;
    }






























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
