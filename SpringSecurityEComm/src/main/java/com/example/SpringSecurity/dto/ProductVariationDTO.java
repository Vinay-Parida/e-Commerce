package com.example.SpringSecurity.dto;

import com.example.SpringSecurity.entity.products.HashMapConverter;

import javax.persistence.Convert;
import java.util.Map;
import java.util.Set;

public class ProductVariationDTO {
    private Long  variationId;
    private Integer quantityAvailable;
    private Double price;
    private Boolean isVariationActive;
    private String primaryImage;
    private Set<String> secondaryImage;
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> metadata;

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getVariationActive() {
        return isVariationActive;
    }

    public void setVariationActive(Boolean variationActive) {
        isVariationActive = variationActive;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public Set<String> getSecondaryImage() {
        return secondaryImage;
    }

    public void setSecondaryImage(Set<String> secondaryImage) {
        this.secondaryImage = secondaryImage;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
