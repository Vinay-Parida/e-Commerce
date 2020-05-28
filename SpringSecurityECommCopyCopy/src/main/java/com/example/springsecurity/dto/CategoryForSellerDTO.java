package com.example.springsecurity.dto;

import java.util.List;

public class CategoryForSellerDTO {

    private Long categoryId;
    private String categoryName;
    private List<CategoryMetadataDTO> categoryMetadataDtoList;
    private List<String> parentCategories;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CategoryMetadataDTO> getCategoryMetadataDtoList() {
        return categoryMetadataDtoList;
    }

    public void setCategoryMetadataDtoList(List<CategoryMetadataDTO> categoryMetadataDtoList) {
        this.categoryMetadataDtoList = categoryMetadataDtoList;
    }

    public List<String> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(List<String> parentCategories) {
        this.parentCategories = parentCategories;
    }
}
