package com.example.SpringSecurity.dto;

import java.util.List;

public class CategoryForSellerDto {

    private Long categoryId;
    private String categoryName;
    private List<CategoryMetadataDto> categoryMetadataDtoList;
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

    public List<CategoryMetadataDto> getCategoryMetadataDtoList() {
        return categoryMetadataDtoList;
    }

    public void setCategoryMetadataDtoList(List<CategoryMetadataDto> categoryMetadataDtoList) {
        this.categoryMetadataDtoList = categoryMetadataDtoList;
    }

    public List<String> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(List<String> parentCategories) {
        this.parentCategories = parentCategories;
    }
}
