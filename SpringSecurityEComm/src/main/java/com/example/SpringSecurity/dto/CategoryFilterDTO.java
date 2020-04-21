package com.example.SpringSecurity.dto;

import java.util.List;
import java.util.Set;

public class CategoryFilterDTO {

    private String categoryName;

    private Set<String> brandList;

    private List<CategoryMetadataDTO> metadataFieldNameAndValues;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<String> getBrandList() {
        return brandList;
    }

    public void setBrandList(Set<String> brandList) {
        this.brandList = brandList;
    }

    public List<CategoryMetadataDTO> getMetadataFieldNameAndValues() {
        return metadataFieldNameAndValues;
    }

    public void setMetadataFieldNameAndValues(List<CategoryMetadataDTO> metadataFieldNameAndValues) {
        this.metadataFieldNameAndValues = metadataFieldNameAndValues;
    }
}
