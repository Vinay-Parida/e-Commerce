package com.example.springsecurity.dto;

import com.example.springsecurity.entity.products.HashMapConverter;

import javax.persistence.Convert;
import java.util.Map;

public class ViewCustomerDTO {
    private Long variationId;

    private String primaryImage;

    @Convert(converter = HashMapConverter.class)
    private Map<String, String> metadata;

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
