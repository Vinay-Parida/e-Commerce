package com.example.springsecurity.factorydesign.factoryinterface;

import com.example.springsecurity.entity.products.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface ProductInterface {
    Product createProduct(String productName);
}
