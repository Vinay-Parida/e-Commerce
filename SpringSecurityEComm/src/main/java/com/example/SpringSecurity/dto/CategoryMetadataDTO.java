package com.example.SpringSecurity.dto;

public class CategoryMetadataDTO {
    private String metadataField;
    private String metadataFieldValue;

    public CategoryMetadataDTO(String metadataField, String metadataFieldValues) {
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
