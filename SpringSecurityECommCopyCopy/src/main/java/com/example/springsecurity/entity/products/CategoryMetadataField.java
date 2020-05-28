package com.example.springsecurity.entity.products;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
    private List<CategoryMetadataFieldValue> categoryMetadataFieldValueList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryMetadataFieldValue> getCategoryMetadataFieldValueList() {
        return categoryMetadataFieldValueList;
    }

    public void setCategoryMetadataFieldValueList(List<CategoryMetadataFieldValue> categoryMetadataFieldValueList) {
        this.categoryMetadataFieldValueList = categoryMetadataFieldValueList;
    }
}
