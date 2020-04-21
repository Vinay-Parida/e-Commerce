package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.products.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {
}
