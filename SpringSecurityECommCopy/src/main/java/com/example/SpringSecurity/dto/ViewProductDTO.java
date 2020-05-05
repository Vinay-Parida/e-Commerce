package com.example.SpringSecurity.dto;

import java.util.List;

public class ViewProductDTO {
    private Long productId;

    private String productName;

    private String brand;

    private String description;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private Boolean isActive;

    private Long categoryId;

    private String categoryName;

    private List<CategoryMetadataDTO> categoryMetadataDTOS;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

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

    public List<CategoryMetadataDTO> getCategoryMetadataDTOS() {
        return categoryMetadataDTOS;
    }

    public void setCategoryMetadataDTOS(List<CategoryMetadataDTO> categoryMetadataDTOS) {
        this.categoryMetadataDTOS = categoryMetadataDTOS;
    }
}
