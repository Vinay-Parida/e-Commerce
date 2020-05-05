package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.products.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField, Long> {
    CategoryMetadataField findByName(String name);

    List<CategoryMetadataField> findAll(Pageable pageable);

}
