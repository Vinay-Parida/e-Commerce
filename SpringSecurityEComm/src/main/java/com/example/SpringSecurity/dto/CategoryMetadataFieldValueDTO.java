package com.example.SpringSecurity.dto;

import java.util.Set;

public class CategoryMetadataFieldValueDTO {
    private Long categoryId;
    private Long fieldId;
    private Set<String> fieldValues;

    public CategoryMetadataFieldValueDTO(Long categoryId, Long fieldId, Set<String> fieldValues) {
        this.categoryId = categoryId;
        this.fieldId = fieldId;
        this.fieldValues = fieldValues;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Set<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Set<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
