package com.example.SpringSecurity.service;

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
import com.example.SpringSecurity.exceptions.ValueNotFoundException;
import com.example.SpringSecurity.repository.*;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Component
public class ProductService {
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
    private UploadImageService uploadImageService;

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
        } else
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
        if (!validateData.equalsIgnoreCase("success"))
            throw new EmailException("Validation Failed");

        ProductVariation productVariation = new ProductVariation();
        Product product = productRepository.findById(addProductVariationDto.getProductId()).get();
        productVariation.setPrice(addProductVariationDto.getPrice());
        productVariation.setProduct(product);
        productVariation.setPrimaryImageName(uploadImageService.uploadPrimaryImage(primaryImage, addProductVariationDto.getProductId(), webRequest));
        productVariation.setQuantityAvailable(addProductVariationDto.getQuantityAvailable());
        productVariation.setMetadata(addProductVariationDto.getMetadata());

        if (!secondaryImages.isEmpty()) {
            Set<String> strings = uploadImageService.uploadSecondaryImage(secondaryImages, webRequest, productVariation.getProduct().getId());
            String convert = SetStringConverter.convertToString(strings);
            productVariation.setSecondaryImage(convert);
        }

        productVariationRepository.save(productVariation);
        return "Product variation Saved successfully";
    }

    private String validateData(AddProductVariationDTO addProductVariationDto, String sellerEmail, WebRequest webRequest) {

        Product product = productRepository.findById(addProductVariationDto.getProductId()).get();

        if (product == null)
            throw new EmailException("Product no available");
        if (!product.getSeller().getEmail().equalsIgnoreCase(sellerEmail))
            throw new EmailException("This is not product of the seller");
        if (addProductVariationDto.getQuantityAvailable() != null) {
            if (addProductVariationDto.getQuantityAvailable() < 0)
                throw new EmailException("Quantity can't be negative");
        }
        if (addProductVariationDto.getPrice() != null) {
            if (addProductVariationDto.getPrice() < 0)
                throw new EmailException("Price can't be negative");
        }
        if (!product.getActive())
            throw new EmailException("Product is not active");
        if (product.getDeleted()) {
            throw new EmailException("Product is deleted");
        }

        Category category = product.getCategory();
        Long categoryId = category.getId();
        Map<String, String> metadata = addProductVariationDto.getMetadata();

        List<String> receivedFields = new ArrayList<>(metadata.keySet());
        List<String> actualFields = new ArrayList<>();
        List<Object> metadataFieldName = categoryMetadataFieldValueRepository.getMetadataFieldName(categoryId);
        metadataFieldName.forEach((e) -> {
            actualFields.add((String) e);
        });
        if (receivedFields.size() < actualFields.size())
            throw new EmailException("Fields are less. Not as same as required fields");

        receivedFields.removeAll(actualFields);
        if (!receivedFields.isEmpty())
            throw new EmailException("Fields are more. Not as same as required fields");

        List<String> metadataFields = new ArrayList<>(metadata.keySet());
        for (String fieldName : metadataFields) {
            CategoryMetadataField field = categoryMetadataFieldRepository.findByName(fieldName);
            List<Object> valueFromCategoryAndField = categoryMetadataFieldValueRepository.getMetadataFieldValuesByCategoryAndFieldId(categoryId, field.getId());
            String values = valueFromCategoryAndField.get(0).toString();
            Set<String> dbValue = SetStringConverter.convertToSet(values);

            String receivedValues = metadata.get(fieldName);
            if (receivedValues.isEmpty())
                throw new EmailException("Values can't be empty");

            Set<String> actualSet = SetStringConverter.convertToSet(receivedValues);
            Set<String> union = Sets.union(actualSet, dbValue);
            if (union.size() > dbValue.size())
                throw new EmailException("This variation value doesn't exists");
        }
        return "Success";
    }

    public ViewProductDTO viewProduct(Long productId, WebRequest webRequest, HttpServletRequest request) {
        String sellerEmail = request.getUserPrincipal().getName();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent())
            throw new EmailException("Invalid Product Id");

        Product product = productOptional.get();
        String email = product.getSeller().getEmail();
        if (!email.equalsIgnoreCase(sellerEmail))
            throw new EmailException("Product doesn't belong to the seller");

        else if (product.getDeleted())
            throw new EmailException("Product is deleted");

        ViewProductDTO viewProductDto = new ViewProductDTO();
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
        List<CategoryMetadataDTO> nameAndValuesDtos = new ArrayList<>();
        List<Object[]> metadataNameAndValues = categoryRepository.getMetadataByCategoryId(category.getId());
        for (Object[] object : metadataNameAndValues) {
            CategoryMetadataDTO categoryMetadataDTO = new CategoryMetadataDTO();
            categoryMetadataDTO.setMetadataField((String) object[0]);
            categoryMetadataDTO.setMetadataFieldValue((String) object[1]);
            nameAndValuesDtos.add(categoryMetadataDTO);
        }
        viewProductDto.setCategoryMetadataDTOS(nameAndValuesDtos);
        return viewProductDto;
    }

    public ViewProductVariationDTO viewProductVariation(Long variationId, WebRequest webRequest, HttpServletRequest request) {

        String sellerEmail = request.getUserPrincipal().getName();
        Optional<ProductVariation> variationOptional = productVariationRepository.findById(variationId);
        if (!variationOptional.isPresent())
            throw new EmailException("Variation doesn't exists");

        ProductVariation productVariation = variationOptional.get();
        Product product = productVariation.getProduct();
        if (product.getDeleted())
            throw new EmailException("Product is deleted");

        String email = product.getSeller().getEmail();
        if (!email.equalsIgnoreCase(sellerEmail))
            throw new EmailException("Product doesn't belong to the seller");


        ViewProductVariationDTO viewProductVariationDto = new ViewProductVariationDTO();
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

        if (productVariation.getSecondaryImage() != null) {
            viewProductVariationDto.setSecondaryImage(SetStringConverter.convertToSet(productVariation.getSecondaryImage()));
        }
        viewProductVariationDto.setMetadata(productVariation.getMetadata());

        return viewProductVariationDto;
    }

    public List<ViewProductDTO> viewAllProduct(String offset, String max, String field, String order, HttpServletRequest request) {
        Integer offSetPage = Integer.parseInt(offset);
        Integer sizeOfPage = Integer.parseInt(max);
        Pageable pageable;
        if (order.equalsIgnoreCase("Ascending")) {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.ASC, field);
        } else {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.DESC, field);
        }
        String sellerEmail = request.getUserPrincipal().getName();
        User user = userRepository.findByEmail(sellerEmail);
        List<Product> allProduct = productRepository.getAllProduct(user.getId(), pageable);
        List<ViewProductDTO> viewProductDtoList = new ArrayList<>();
        for (Product product : allProduct) {
            ViewProductDTO viewProductDto = new ViewProductDTO();
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
            List<CategoryMetadataDTO> nameAndValuesDtos = new ArrayList<>();
            List<Object[]> metadataNameAndValues = categoryRepository.getMetadataByCategoryId(category.getId());
            for (Object[] object : metadataNameAndValues) {
                CategoryMetadataDTO metadataFieldNameAndValuesDto = new CategoryMetadataDTO();
                metadataFieldNameAndValuesDto.setMetadataField((String) object[0]);
                metadataFieldNameAndValuesDto.setMetadataFieldValue((String) object[1]);
                nameAndValuesDtos.add(metadataFieldNameAndValuesDto);
            }
            viewProductDto.setCategoryMetadataDTOS(nameAndValuesDtos);
            viewProductDtoList.add(viewProductDto);
        }
        return viewProductDtoList;
    }

    public List<ViewAllProductVariationDTO> viewAllProductVariation(Long productId, String offset, String max, String field, String order, HttpServletRequest request, WebRequest webRequest) {
        String sellerEmail = request.getUserPrincipal().getName();
        Integer offSetPage = Integer.parseInt(offset);
        Integer sizeOfPage = Integer.parseInt(max);
        Pageable pageable;
        if (order.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.ASC, field);
        } else {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.DESC, field);
        }
        List<ProductVariation> productVariations = productVariationRepository.getAllVariationByProductId(productId, pageable);
        if (productVariations.isEmpty())
            throw new EmailException("Product Variation doesn't exist");

        Product product = productRepository.findById(productId).get();
        List<ViewAllProductVariationDTO> productVariationDtoList = new ArrayList<>();

        if (product == null)
            throw new ProductException("Invalid Product");

        if (product.getDeleted())
            throw new ProductException("Product is deleted");

        String email = product.getSeller().getEmail();
        if (!email.equalsIgnoreCase(sellerEmail))
            throw new EmailException("Seller have no product variation");

        for (ProductVariation productVariation : productVariations) {
            ViewAllProductVariationDTO productVariationDto = setAllVariation(product, productVariation);
            productVariationDtoList.add(productVariationDto);
        }
        return productVariationDtoList;
    }

    private ViewAllProductVariationDTO setAllVariation(Product product, ProductVariation productVariation) {
        ViewAllProductVariationDTO viewProductVariationDTO = new ViewAllProductVariationDTO();
        viewProductVariationDTO.setVariationId(productVariation.getId());
        viewProductVariationDTO.setProductName(product.getName());
        viewProductVariationDTO.setProductBrand(product.getBrand());
        viewProductVariationDTO.setProductDescription(product.getDescription());
        viewProductVariationDTO.setPrice(productVariation.getPrice());
        viewProductVariationDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
        viewProductVariationDTO.setVariationActive(productVariation.getActive());
        viewProductVariationDTO.setCancellable(product.getCancellable());
        viewProductVariationDTO.setReturnable(product.getReturnable());
        viewProductVariationDTO.setPrimaryImage(productVariation.getPrimaryImageName());
        if (productVariation.getSecondaryImage() != null) {
            viewProductVariationDTO.setSecondaryImage(SetStringConverter.convertToSet(productVariation.getSecondaryImage()));
        }
        viewProductVariationDTO.setMetadata(productVariation.getMetadata());
        return viewProductVariationDTO;
    }

    public String deleteProduct(Long productId, HttpServletRequest request, WebRequest webRequest) {

        String sellerEmail = request.getUserPrincipal().getName();
        Optional<Product> productOptional = productRepository.findById(productId);

        if (!productOptional.isPresent())
            throw new EmailException("Invalid Product");

        Product product = productOptional.get();
        String email = product.getSeller().getEmail();
        if (!sellerEmail.equalsIgnoreCase(email))
            throw new EmailException("Product doesn't belong to seller");

        product.setDeleted(true);
        return "Product deleted successfully";
    }

    public String updateProduct(UpdateProductDTO updateProductDto, HttpServletRequest request, WebRequest webRequest) {
        String sellerEmail = request.getUserPrincipal().getName();
        Long productId = updateProductDto.getProductId();
        Optional<Product> productOptional = productRepository.findById(productId);

        if (!productOptional.isPresent())
            throw new ProductException("Invalid Product Id");

        Product product = productOptional.get();
        String email = product.getSeller().getEmail();

        if (!sellerEmail.equalsIgnoreCase(email))
            throw new EmailException("Product doesn't belong to the seller");

        if (updateProductDto.getName() != null) {
            Product productByName = productRepository.getUniqueProduct(product.getBrand(), product.getCategory().getId(), product.getSeller().getId(), updateProductDto.getName());
            if (productByName != null)
                throw new EmailException("Product already exist with this name");
        }
        product.setName(updateProductDto.getName());

        if (updateProductDto.getDescription() != null) {
            product.setDescription(updateProductDto.getDescription());
        }
        //Cancellable and returnable are taking null: CHECK LATER
        if (updateProductDto.getCancellable() != null) {
            product.setCancellable(updateProductDto.getCancellable());
        }
        if (updateProductDto.getReturnable() != null) {
            product.setReturnable(updateProductDto.getReturnable());
        }
        productRepository.save(product);

        return "Product updated successfully";
    }

    public String updateProductVariation(MultipartFile primaryImage, List<MultipartFile> secondaryImages,HttpServletRequest request, UpdateProductVariationDTO updateProductVariationDto,WebRequest webRequest) throws IOException {
        Locale locale=webRequest.getLocale();
        Optional<ProductVariation> variationOptional = productVariationRepository.findById(updateProductVariationDto.getProductVariationId());
        String sellerEmail = request.getUserPrincipal().getName();
        String validateData = validateUpdateData(updateProductVariationDto, sellerEmail, webRequest);
        if(!validateData.equalsIgnoreCase("success")){
            throw  new ProductException("Validation failed");
        }
        ProductVariation productVariation= variationOptional.get();
        if(updateProductVariationDto.getPrice()!=null) {
            productVariation.setPrice(updateProductVariationDto.getPrice());
        }
        if(primaryImage!=null) {
            productVariation.setPrimaryImageName(uploadImageService.uploadPrimaryImage(primaryImage, productVariation.getProduct().getId(), webRequest));
        }
        productVariation.setQuantityAvailable(updateProductVariationDto.getQuantityAvailable());
        productVariation.setMetadata(updateProductVariationDto.getMetadata());
        if(updateProductVariationDto.getActive()!=null) {
            productVariation.setActive(updateProductVariationDto.getActive());
        }
        if(!secondaryImages.isEmpty()){
            Set<String> strings = uploadImageService.uploadSecondaryImage(secondaryImages, webRequest, productVariation.getProduct().getId());
            String convert = SetStringConverter.convertToString(strings);
            productVariation.setSecondaryImage(convert);
        }
        productVariationRepository.save(productVariation);

        return "Product variation Updated";
    }

    //Same problem with IsActive. CHECK LATER
    private String validateUpdateData(UpdateProductVariationDTO updateProductVariationDto,String sellerEmail,WebRequest webRequest){

        Optional<ProductVariation> variationOptional = productVariationRepository.findById(updateProductVariationDto.getProductVariationId());

        if(!variationOptional.isPresent())
            throw new ProductException("Product Variation is not valid");

        ProductVariation productVariation = variationOptional.get();
        Product product =productVariation.getProduct();

        if(!product.getActive())
            throw new ProductException("Product is not active");

        if(!product.getSeller().getEmail().equalsIgnoreCase(sellerEmail))
            throw new ProductException("Product doesn't belong to seller");

        if(updateProductVariationDto.getQuantityAvailable()!=null){
            if(updateProductVariationDto.getQuantityAvailable()<0 )
                throw new ProductException("Quantity can't be negative");
        }
        if(updateProductVariationDto.getPrice()!=null) {
            if (updateProductVariationDto.getPrice() < 0)
                throw new ProductException("Price can't be negative");
        }
        if(product.getDeleted())
            throw new ProductException("Product is deleted");


        Category category=product.getCategory();
        Long categoryId = category.getId();
        if(updateProductVariationDto.getMetadata()!=null) {
            Map<String, String> metadata = updateProductVariationDto.getMetadata();

            List<String> receivedFields = new ArrayList<>(metadata.keySet());
            List<String> actualFields = new ArrayList<>();
            List<Object> metadataFieldName = categoryMetadataFieldValueRepository.getMetadataFieldName(categoryId);
            metadataFieldName.forEach((e) -> {
                actualFields.add((String) e);
            });

            if (receivedFields.size() < actualFields.size())
                throw new ProductException("Entered field is less than existing field");

            receivedFields.removeAll(actualFields);

            if (!receivedFields.isEmpty()) {
                throw new EmailException("Entered field is more than existing fields");
            }

            List<String> metadataFields = new ArrayList<>(metadata.keySet());
            for (String fieldName : metadataFields) {
                CategoryMetadataField field = categoryMetadataFieldRepository.findByName(fieldName);
                List<Object> valueFromCategoryAndField = categoryMetadataFieldValueRepository.getMetadataFieldValuesByCategoryAndFieldId(categoryId, field.getId());
                String values = valueFromCategoryAndField.get(0).toString();
                Set<String> dbValue = SetStringConverter.convertToSet(values);

                String receivedValues = metadata.get(fieldName);
                if (receivedValues.isEmpty()) {
                    throw new EmailException("Values can't be empty");
                }
                Set<String> actualSet = SetStringConverter.convertToSet(receivedValues);
                Set<String> union = Sets.union(actualSet, dbValue);
                if (union.size() > dbValue.size()) {
                    throw new EmailException("This variation doesn't exists");
                }
            }
        }
        return "Success";
    }

    public ViewCustomerProductDTO viewCustomerProduct(Long productId,WebRequest webRequest){

        Optional<Product> productOptional = productRepository.findById(productId);
        if(!productOptional.isPresent()){
            throw new EmailException("ProductId is invalid");
        }
        Product product = productOptional.get();
        if(product.getDeleted()){
            throw new EmailException("Product is deleted");
        }
        if(!product.getActive()){
            throw new EmailException("Product is inactive");
        }
        Category category = product.getCategory();
        List<ProductVariation> variationList = productVariationRepository.findByProductId(productId);
        if(variationList.isEmpty()){
            throw  new EmailException("This product have no variations available");
        }

        ViewCustomerProductDTO viewCustomerProductDTO =new ViewCustomerProductDTO();
        viewCustomerProductDTO.setProductName(product.getName());
        viewCustomerProductDTO.setBrand(product.getBrand());
        viewCustomerProductDTO.setDescription(product.getDescription());
        viewCustomerProductDTO.setCancellable(product.getCancellable());
        viewCustomerProductDTO.setReturnable(product.getReturnable());
        viewCustomerProductDTO.setCategoryId(category.getId());
        viewCustomerProductDTO.setCategoryName(category.getName());

        List<ProductVariationDTO> variationDtoList=new ArrayList<>();
        for (ProductVariation productVariation:variationList) {
            ProductVariationDTO variationDto=new ProductVariationDTO();
            variationDto.setQuantityAvailable(productVariation.getQuantityAvailable());
            variationDto.setPrice(productVariation.getPrice());
            variationDto.setMetadata(productVariation.getMetadata());
            variationDto.setVariationId(productVariation.getId());
            variationDto.setVariationActive(productVariation.getActive());
            variationDto.setPrimaryImage(productVariation.getPrimaryImageName());
            variationDto.setSecondaryImage(SetStringConverter.convertToSet(productVariation.getSecondaryImage()));
            variationDtoList.add(variationDto);
        }
        viewCustomerProductDTO.setVariation(variationDtoList);
        return viewCustomerProductDTO;
    }


    public List<ViewCustomerAllProductDTO> viewCustomerAllProduct(Long categoryId,String offset,WebRequest webRequest, String max,  String field, String order) {

        Integer offSetPage = Integer.parseInt(offset);
        Integer sizeOfPage = Integer.parseInt(max);
        Pageable pageable;
        if(order.equalsIgnoreCase("Ascending")) {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.ASC, field);
        }else{
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.DESC, field);
        }
        List<Category> categoryList = categoryRepository.findByParentId(categoryId);
        if(categoryList.isEmpty()){
            return getProductDetails(categoryId,pageable);
        }else{
            throw new ValueNotFoundException("Category is not leaf node");
        }
    }
    private List<ViewCustomerAllProductDTO> getProductDetails(Long categoryId, Pageable pageable){
        List<ViewCustomerAllProductDTO> viewCustomerAllProductDtos=new ArrayList<>();
        List<Product> productList = productRepository.findByCategoryId(categoryId, pageable);
        for (Product product:productList) {
            if (!product.getDeleted() && product.getActive()) {
                ViewCustomerAllProductDTO viewCustomerAllProductDto = setProductData(product);
                viewCustomerAllProductDtos.add(viewCustomerAllProductDto);
            }
        }
        return viewCustomerAllProductDtos;
    }
    private ViewCustomerAllProductDTO setProductData(Product product){
        ViewCustomerAllProductDTO viewCustomerAllProductDto = new ViewCustomerAllProductDTO();
        viewCustomerAllProductDto.setProductId(product.getId());
        viewCustomerAllProductDto.setProductName(product.getName());
        Category category = product.getCategory();
        viewCustomerAllProductDto.setCategoryId(category.getId());
        viewCustomerAllProductDto.setCategoryName(category.getName());
        List<ProductVariation> variationList = productVariationRepository.findByProductId(product.getId());
        List<ViewCustomerDTO> viewcustomerDtos = new ArrayList<>();
        for (ProductVariation productVariation : variationList) {
            ViewCustomerDTO viewcustomerDto = new ViewCustomerDTO();
            viewcustomerDto.setVariationId(productVariation.getId());
            viewcustomerDto.setMetadata(productVariation.getMetadata());
            viewcustomerDto.setPrimaryImage(productVariation.getPrimaryImageName());
            viewcustomerDtos.add(viewcustomerDto);
        }
        viewCustomerAllProductDto.setVariation(viewcustomerDtos);
        return viewCustomerAllProductDto;
    }

    public List<ViewCustomerAllProductDTO> viewSimilarProduct(Long productId,WebRequest webRequest,String offset,  String max,  String field, String order){
        Locale locale=webRequest.getLocale();
        Optional<Product> productOptional = productRepository.findById(productId);
        if(!productOptional.isPresent()){
            throw new EmailException("Invalid ProductId");
        }
        Long categoryId = productOptional.get().getCategory().getId();
        return viewCustomerAllProduct(categoryId,offset,webRequest,max,field,order);
    }

    public ViewCustomerProductDTO viewAdminProduct(Long productId,WebRequest webRequest){
        Locale locale=webRequest.getLocale();
        Optional<Product> productOptional = productRepository.findById(productId);
        if(!productOptional.isPresent()){
            throw new EmailException("ProductId is invalid");
        }
        Product product = productOptional.get();
        Category category = product.getCategory();
        List<ProductVariation> variationList = productVariationRepository.findByProductId(productId);
        if(variationList.isEmpty()){
            throw  new EmailException("Variation doesn't exists");
        }
        ViewCustomerProductDTO viewCustomerProductDTO =new ViewCustomerProductDTO();
        viewCustomerProductDTO.setProductName(product.getName());
        viewCustomerProductDTO.setBrand(product.getBrand());
        viewCustomerProductDTO.setDescription(product.getDescription());
        viewCustomerProductDTO.setCancellable(product.getCancellable());
        viewCustomerProductDTO.setReturnable(product.getReturnable());
        viewCustomerProductDTO.setCategoryId(category.getId());
        viewCustomerProductDTO.setCategoryName(category.getName());

        List<ProductVariationDTO> variationDtoList=new ArrayList<>();
        for (ProductVariation productVariation:variationList) {
            ProductVariationDTO variationDto=new ProductVariationDTO();
            variationDto.setQuantityAvailable(productVariation.getQuantityAvailable());
            variationDto.setPrice(productVariation.getPrice());
            variationDto.setMetadata(productVariation.getMetadata());
            variationDto.setVariationId(productVariation.getId());
            variationDto.setVariationActive(productVariation.getActive());
            variationDto.setPrimaryImage(productVariation.getPrimaryImageName());
            variationDto.setSecondaryImage(SetStringConverter.convertToSet(productVariation.getSecondaryImage()));
            variationDtoList.add(variationDto);
        }
        viewCustomerProductDTO.setVariation(variationDtoList);
        return viewCustomerProductDTO;
    }

    public List<ViewCustomerAllProductDTO> viewAllProduct(String offset,  String max,  String field, String order){
        Integer offSetPage = Integer.parseInt(offset);
        Integer sizeOfPage = Integer.parseInt(max);
        Pageable pageable;
        if(order.equalsIgnoreCase("Ascending")) {
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.ASC, field);
        }else{
            pageable = PageRequest.of(offSetPage, sizeOfPage, Sort.Direction.DESC, field);
        }
        List<Product> productList = (List<Product>) productRepository.findAll();
        List<ViewCustomerAllProductDTO> viewCustomerAllProductDtos=new ArrayList<>();
        for (Product product:productList) {
            if (!product.getDeleted() && product.getActive()) {
                ViewCustomerAllProductDTO viewCustomerAllProductDto = setProductData(product);
                viewCustomerAllProductDtos.add(viewCustomerAllProductDto);
            }
        }
        return viewCustomerAllProductDtos;
    }

    public String activateProduct(Long productId, WebRequest webRequest) {
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

    public String deactivateProduct(Long productId, WebRequest webRequest) {
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
