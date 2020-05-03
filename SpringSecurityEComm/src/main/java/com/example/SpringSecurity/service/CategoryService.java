package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.*;
import com.example.SpringSecurity.entity.products.*;
import com.example.SpringSecurity.exceptions.CategoryException;
import com.example.SpringSecurity.exceptions.ValueNotFoundException;
import com.example.SpringSecurity.repository.CategoryMetadataFieldRepository;
import com.example.SpringSecurity.repository.CategoryMetadataFieldValueRepository;
import com.example.SpringSecurity.repository.CategoryRepository;
import com.example.SpringSecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Component
public class CategoryService {

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetadataFieldValueRepository categoryMetadataFieldValueRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageSource messageSource;

    public String addMetadataField(String fieldName, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        if (fieldName ==null || fieldName =="") {
            throw new ValueNotFoundException("Please Enter fieldName");
        }
        else {
            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByName(fieldName);
            if(categoryMetadataField != null){
                return messageSource.getMessage("metadata.field.already.exists", null, locale) + categoryMetadataField.getId();
            }
            else {
                CategoryMetadataField categoryMetadataFieldObject = new CategoryMetadataField();
                categoryMetadataFieldObject.setName(fieldName);
                categoryMetadataFieldRepository.save(categoryMetadataFieldObject);

                return messageSource.getMessage("metadata.field.saved.success", null, locale) + categoryMetadataFieldRepository.findByName(fieldName).getId();
            }
        }
    }


    public List<CategoryMetadataField> getAllMetadataFields(Integer size, Integer offset, String field, String order){
        Pageable pageable;

        if (order.equalsIgnoreCase("ASC"))
            pageable = PageRequest.of(offset, size, Sort.Direction.ASC, field);
        else
            pageable = PageRequest.of(offset, size, Sort.Direction.DESC, field);

        return categoryMetadataFieldRepository.findAll(pageable);
    }



    public String addCategory(String categoryName, Long parentId, WebRequest webRequest){
        Locale locale = webRequest.getLocale();

        if(checkNull(categoryName) && parentId != null){
            if(categoryRepository.findByName(categoryName)!= null){
                String messageCategoryAlreadyExists = messageSource.getMessage("category.already.exists", null, locale);
                throw new CategoryException(messageCategoryAlreadyExists);
            }
            else if(!categoryRepository.findById(parentId).isPresent()){
                String messageParentDoesNotExist = messageSource.getMessage("category.parent.does.not.exists", null, locale);
                throw new CategoryException(messageParentDoesNotExist);
            }
            else {
                Category category = new Category();
                category.setName(categoryName);
                category.setParentId(parentId);
                categoryRepository.save(category);
                String messageCategorySaved = messageSource.getMessage("category.saved.success", null, locale);
                return messageCategorySaved + categoryRepository.findByName(categoryName).getId();
            }
        }
        else if (checkNull(categoryName) && parentId == null){
            if (categoryRepository.findByName(categoryName) != null){
                String messageCategoryAlreadyExists = messageSource.getMessage("category.already.exists", null, locale);
                throw new CategoryException(messageCategoryAlreadyExists);
            }
            else {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
                String messageCategorySaved = messageSource.getMessage("category.saved.success", null, locale);
                return messageCategorySaved + categoryRepository.findByName(categoryName).getId();
            }
        }
        else {
            throw new ValueNotFoundException("Enter Category name");
        }
    }

    private Boolean checkNull(String value){
        if(value != null && !value.equals(""))
            return true;
        else
            return false;
    }

    public SingleCategoryDTO getSingleCategory(Long categoryId, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        Category category = categoryRepository.findByid(categoryId);

        List<String> emptyList = new ArrayList<>();
        List<String> categoriesListWithParent = getCategoriesParent(categoryId, emptyList);
        List<Category> childCategory = categoryRepository.findByParentId(category.getId());

        SingleCategoryDTO singleCategoryDto = new SingleCategoryDTO();
        singleCategoryDto.setId(categoryId);
        singleCategoryDto.setCategoriesList(categoriesListWithParent);
        if (!childCategory.isEmpty()){
            singleCategoryDto.setChildCategory(childCategory.get(0).getName());
        }
        else {
            String messageChildDoesNotExists = messageSource.getMessage("category.child.does.not.exists", null, locale);
            singleCategoryDto.setChildCategory(messageChildDoesNotExists);
        }

        return singleCategoryDto;
    }

    private List<String> getCategoriesParent(Long id, List<String> categoriesList){
        Category category = categoryRepository.findByid(id);
        categoriesList.add(category.getName());
        if (category.getParentId() != null){
            return getCategoriesParent(category.getParentId(), categoriesList);
        }
        else {
            return categoriesList;
        }
    }


    public List<SingleCategoryDTO> getAllCategory(Integer size, Integer offset, String field, String order, WebRequest webRequest){
        Pageable pageable;

        if (order.equalsIgnoreCase("ASC"))
            pageable = PageRequest.of(offset, size, Sort.Direction.ASC, field);
        else
            pageable = PageRequest.of(offset, size, Sort.Direction.DESC, field);

        List<SingleCategoryDTO> singleCategoryDtoList = new ArrayList<>();
        List<Category> allCategory = categoryRepository.findAll(pageable);

        allCategory.forEach(category -> {
            singleCategoryDtoList.add(getSingleCategory(category.getId(), webRequest));
        });
        return singleCategoryDtoList;
    }



    public String updateCategory(Long categoryId, String categoryName, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        Category category = categoryRepository.findByid(categoryId);

        if (category != null){
            if (categoryRepository.findByName(categoryName) == null){
                category.setName(categoryName);
                categoryRepository.save(category);
                String messageCategoryUpdateSuccess = messageSource.getMessage("category.update.success", null, locale);
                return messageCategoryUpdateSuccess;
            }
            else {
                String messageCategoryAlreadyExists = messageSource.getMessage("category.already.exists", null, locale);
                throw new CategoryException(messageCategoryAlreadyExists);
            }
        }
        else {
            String messageCategoryIdDoesNotExists = messageSource.getMessage("category.does.not.exists", null, locale);
            throw new CategoryException(messageCategoryIdDoesNotExists);
        }
    }



    public String addMetadataFieldValue(CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        Optional<Category> category = categoryRepository.findById(categoryMetadataFieldValueDto.getCategoryId());
        Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(categoryMetadataFieldValueDto.getFieldId());

        if (category.isPresent()){
            if (categoryMetadataField.isPresent()){
                CategoryMetadataFieldValue categoryMetadataFieldValue = new CategoryMetadataFieldValue();
                CategoryMetadataFieldValueId categoryMetadataFieldValueId = new CategoryMetadataFieldValueId();

                categoryMetadataFieldValueId.setCategory_id(category.get().getId());
                categoryMetadataFieldValueId.setCategory_metadata_field_id(categoryMetadataField.get().getId());

                categoryMetadataFieldValue.setCategoryMetadataFieldValueId(categoryMetadataFieldValueId);
                categoryMetadataFieldValue.setCategory(category.get());
                categoryMetadataFieldValue.setCategoryMetadataField(categoryMetadataField.get());

                String values = SetStringConverter.convertToString(categoryMetadataFieldValueDto.getFieldValues());
                categoryMetadataFieldValue.setValues(values);

                categoryMetadataFieldValueRepository.save(categoryMetadataFieldValue);

                String messageFieldValuedAdded = messageSource.getMessage("category.metadata.field.value.saved.success", null, locale);
                return messageFieldValuedAdded;
            }
            else
                throw new CategoryException(messageSource.getMessage("category.metadata.field.not.exists", null, locale));
        }
        else {
            String messageCategoryIdDoesNotExists = messageSource.getMessage("category.does.not.exists", null, locale);
            throw new CategoryException(messageCategoryIdDoesNotExists);
        }
    }



    public String updateMetadataField(CategoryMetadataFieldValueDTO categoryMetadataFieldValueDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        CategoryMetadataFieldValue categoryMetadataFieldValue = categoryMetadataFieldValueRepository
                                                                    .findByCategoryAndMetadataField(categoryMetadataFieldValueDto.getCategoryId(),
                                                                                                    categoryMetadataFieldValueDto.getFieldId());
        if (categoryMetadataFieldValue!= null){
            String updateValues = validateMetadataFieldValues(categoryMetadataFieldValueDto.getFieldValues(),
                                                                categoryMetadataFieldValueDto.getCategoryId(),
                                                                categoryMetadataFieldValueDto.getFieldId(), webRequest);
            categoryMetadataFieldValue.setValues(updateValues);
            categoryMetadataFieldValueRepository.save(categoryMetadataFieldValue);
            String messageFieldValuedUpdate = messageSource.getMessage("category.metadata.field.update.success", null, locale);
            return messageFieldValuedUpdate;
        }
        else {
            String messageCategoryMetadataFieldException = messageSource.getMessage("category.metadata.field.exception.enter.valid.value", null, locale);
            throw new CategoryException(messageCategoryMetadataFieldException);
        }
    }


    private String validateMetadataFieldValues(Set<String> fieldValues, Long categoryId, Long fieldId, WebRequest webRequest){
        String value = categoryMetadataFieldValueRepository.findByCategoryAndMetadataField(categoryId, fieldId).getValues();
        if (!fieldValues.isEmpty()){
            Set<String> valueSet = SetStringConverter.convertToSet(value);
            Set<String> intersectionSet = findIntersection(fieldValues, valueSet);
            if (intersectionSet.isEmpty()){
                String updatedValue = value + "," + SetStringConverter.convertToString(fieldValues);
                return updatedValue;
            }
            else {
                throw new CategoryException("Field Already Exists: " + SetStringConverter.convertToString(intersectionSet));
            }
        }
        else {
            throw new CategoryException("Enter at least one field value to update");
        }

    }

    private Set<String> findIntersection(Set<String> stringSet1, Set<String> stringSet2){
        Set<String> intersection = new HashSet<>(stringSet1);
        intersection.retainAll(stringSet2);
        return intersection;
    }


    public List<CategoryForSellerDTO> getCategoryForSeller(){

        List<CategoryForSellerDTO> categorySellerDtos=new ArrayList<>();
        List<Category> leafCategories=categoryRepository.getLeafCategory();
        leafCategories.forEach(category -> {
            CategoryForSellerDTO categorySellerDto=new CategoryForSellerDTO();
            categorySellerDto.setCategoryId(category.getId());
            categorySellerDto.setCategoryName(category.getName());
            categorySellerDto.setCategoryMetadataDtoList(getCategoryMetadata(category.getId()));
            Long parentId=category.getParentId();
            if (parentId!=null){
                List<String> parentCategories=new ArrayList<>();
                categorySellerDto.setParentCategories(getCategoriesParent(parentId,parentCategories));
            }
            categorySellerDtos.add(categorySellerDto);
        });
        return categorySellerDtos;
    }

    private List<CategoryMetadataDTO> getCategoryMetadata(Long id) {
        List<CategoryMetadataDTO> categoryMetadataDtoList=new ArrayList<>();
        List<Object[]> metadataList=categoryRepository.getMetadataByCategoryId(id);
        for (Object[] metadata:metadataList) {
            CategoryMetadataDTO categoryMetadataDto=new CategoryMetadataDTO((String) metadata[0],(String) metadata[1]);
            categoryMetadataDtoList.add(categoryMetadataDto);
        }
        return categoryMetadataDtoList;
    }


    public List<CategoryDTO> getAllCategoryForCustomer(Long categoryId){
        List<CategoryDTO> customerCategoryDtos=new ArrayList<>();
        if(categoryId==null){
            List<Category> rootParent = categoryRepository.getRootParent();
            for (Category category: rootParent) {
                CategoryDTO customerCategoryDto=new CategoryDTO();
                customerCategoryDto.setCategoryId(category.getId());
                customerCategoryDto.setCategoryName(category.getName());
                customerCategoryDtos.add(customerCategoryDto);
            }
        }else{
            List<Category> categoryList = categoryRepository.findByParentId(categoryId);
            for (Category category: categoryList) {
                CategoryDTO customerCategoryDto=new CategoryDTO();
                customerCategoryDto.setCategoryId(category.getId());
                customerCategoryDto.setCategoryName(category.getName());
                customerCategoryDtos.add(customerCategoryDto);
            }
        }
        return customerCategoryDtos;
    }


    public CategoryFilterDTO getFilterData(Long categoryId, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        Category category = categoryRepository.findByid(categoryId);
        if(category==null){
            String messageCategoryIdDoesNotExists = messageSource.getMessage("category.does.not.exists", null, locale);
            throw new CategoryException(messageCategoryIdDoesNotExists);
        }
        List<Product> productList = productRepository.getProduct(categoryId);
        Set<String> brandList=new HashSet<>();
        for (Product product:productList) {
            brandList.add(product.getBrand());
        }
        CategoryFilterDTO categoryFilteringDto=new CategoryFilterDTO();
        List<CategoryMetadataDTO> categoryMetadataDTOS=new ArrayList<>();
        categoryFilteringDto.setCategoryName(category.getName());
        List<Object[]> nameAndValues = categoryRepository.getMetadataByCategoryId(categoryId);
        for (Object[] objects:nameAndValues) {
            CategoryMetadataDTO categoryMetadataDTO=new CategoryMetadataDTO();
            categoryMetadataDTO.setMetadataField((String)objects[0]);
            categoryMetadataDTO.setMetadataFieldValue((String)objects[1]);
            categoryMetadataDTOS.add(categoryMetadataDTO);
        }
        categoryFilteringDto.setCategoryName(category.getName());
        categoryFilteringDto.setBrandList(brandList);
        categoryFilteringDto.setMetadataFieldNameAndValues(categoryMetadataDTOS);
        return categoryFilteringDto;
    }


}
