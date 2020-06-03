package com.example.springsecurity.factorydesign.factoryinterface;

import com.example.springsecurity.dto.AddProductDTO;
import com.example.springsecurity.entity.products.Product;

public interface ProductInterface {
    Product createProduct(AddProductDTO product);
}
