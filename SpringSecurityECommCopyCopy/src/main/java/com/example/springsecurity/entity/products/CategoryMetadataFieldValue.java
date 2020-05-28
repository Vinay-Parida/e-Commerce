package com.example.springsecurity.entity.products;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class CategoryMetadataFieldValue {

    @EmbeddedId
    private CategoryMetadataFieldValueId categoryMetadataFieldValueId;

    @ManyToOne
    @MapsId("category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne
    @MapsId("category_id")
    private Category category;

    private String value;

    public CategoryMetadataFieldValueId getCategoryMetadataFieldValueId() {
        return categoryMetadataFieldValueId;
    }

    public void setCategoryMetadataFieldValueId(CategoryMetadataFieldValueId categoryMetadataFieldValueId) {
        this.categoryMetadataFieldValueId = categoryMetadataFieldValueId;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getValues() {
        return value;
    }

    public void setValues(String values) {
        this.value = values;
    }
}
