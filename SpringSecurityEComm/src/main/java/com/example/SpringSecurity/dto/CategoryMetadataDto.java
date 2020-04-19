package com.example.SpringSecurity.dto;

public class CategoryMetadataDto {
    private String metadataField;
    private String metadataFieldValue;

    public CategoryMetadataDto(String metadataField, String metadataFieldValues) {
        this.metadataField = metadataField;
        this.metadataFieldValue = metadataFieldValues;
    }

    public String getMetadataField() {
        return metadataField;
    }

    public void setMetadataField(String metadataField) {
        this.metadataField = metadataField;
    }

    public String getMetadataFieldValue() {
        return metadataFieldValue;
    }

    public void setMetadataFieldValue(String metadataFieldValue) {
        this.metadataFieldValue = metadataFieldValue;
    }
}
