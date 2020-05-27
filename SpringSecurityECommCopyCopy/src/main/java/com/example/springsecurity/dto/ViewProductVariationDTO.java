package com.example.springsecurity.dto;

import com.example.springsecurity.entity.products.HashMapConverter;

import javax.persistence.Convert;
import java.util.Map;
import java.util.Set;

public class ViewProductVariationDTO {
    private Long productId;

    private String productName;

    private String productBrand;

    private String productDescription;

    private Integer quantityAvailable;

    private Double price;

    private Boolean isVariationActive;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private String primageImage;

    private Set<String> secondaryImage;

    @Convert(converter = HashMapConverter.class)
    private Map<String, String> metadata;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getPrimageImage() {
        return primageImage;
    }

    public void setPrimageImage(String primageImage) {
        this.primageImage = primageImage;
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
