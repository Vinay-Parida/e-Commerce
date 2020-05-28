package com.example.springsecurity.controller;

import com.example.springsecurity.repository.criteriaqueryrepository.CriteriaQueryRepository;
import com.example.springsecurity.repository.criteriaqueryrepository.JoinCriteriaQueryRepository;
import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.CategoryMetadataField;
import com.example.springsecurity.repository.UserRepository;
import com.jfilter.filter.FieldFilterSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CriteriaController {

    @Autowired
    private CriteriaQueryRepository queryService;

    @Autowired
    private JoinCriteriaQueryRepository joinQueryService;

    @Autowired
    private UserRepository userRepository;

    @FieldFilterSetting(className = Category.class, fields = {"productSet","categoryMetadataFieldValueList"})
    @GetMapping("/categoriesList")
    public List<Category> allCategories(){
        return queryService.categoryList();
    }

    @FieldFilterSetting(className = Category.class, fields = {"productSet","categoryMetadataFieldValueList"})
    @FieldFilterSetting(className = CategoryMetadataField.class, fields = {"categoryMetadataFieldValueList"})
    @GetMapping("/metadataFieldValuesWithCategory")
    public List<Object[]> allMetadataFieldValuesWithCategory(){
        return joinQueryService.categoryAndMetadataFieldList();
    }

    @FieldFilterSetting(className = Category.class, fields = {"productSet","categoryMetadataFieldValueList"})
    @FieldFilterSetting(className = CategoryMetadataField.class, fields = {"categoryMetadataFieldValueList"})
    @GetMapping("/metadataFieldValuesWithCategoryJoin")
    public List<Object[]> allMetadataFieldValuesWithCategoryAllJoin(){
        return joinQueryService.categoryAndMetadataFieldListJoin();
    }

    @FieldFilterSetting(className = Category.class, fields = {"productSet","categoryMetadataFieldValueList"})
    @GetMapping("/joinCategoryAndValue")
    public List<Category> join(){
        return joinQueryService.categoryListJoin();
    }

    @FieldFilterSetting(className = Category.class, fields = {"productSet","categoryMetadataFieldValueList"})
    @FieldFilterSetting(className = CategoryMetadataField.class, fields = {"categoryMetadataFieldValueList"})
    @GetMapping("/joinValueAndCategory")
    public List<CategoryMetadataField> joins(){
        return joinQueryService.categoryAndValueListJoin();
    }


    @FieldFilterSetting(className = CategoryMetadataField.class, fields = {"categoryMetadataFieldValueList"})
    @GetMapping("/tripleJoin")
    public List<CategoryMetadataField> tripleJoin(){
        return joinQueryService.tripleJoin();
    }
}
