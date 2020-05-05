package com.example.SpringSecurity.entity.products;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;
    private Long parentId;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> productSet;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public List<CategoryMetadataFieldValue> getCategoryMetadataFieldValueList() {
        return categoryMetadataFieldValueList;
    }

    public void setCategoryMetadataFieldValueList(List<CategoryMetadataFieldValue> categoryMetadataFieldValueList) {
        this.categoryMetadataFieldValueList = categoryMetadataFieldValueList;
    }
}
