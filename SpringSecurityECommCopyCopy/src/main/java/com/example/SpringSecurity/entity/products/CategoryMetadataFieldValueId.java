package com.example.SpringSecurity.entity.products;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoryMetadataFieldValueId implements Serializable {

    //Variable don't follow naming convention because it maps variable from database they both should be same
    private Long category_id;
    private Long category_metadata_field_id;

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getCategory_metadata_field_id() {
        return category_metadata_field_id;
    }

    public void setCategory_metadata_field_id(Long category_metadata_field_id) {
        this.category_metadata_field_id = category_metadata_field_id;
    }
}
