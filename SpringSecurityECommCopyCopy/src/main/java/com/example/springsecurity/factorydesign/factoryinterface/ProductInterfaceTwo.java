package com.example.springsecurity.factorydesign.factoryinterface;

import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.Product;

import java.util.List;

public interface ProductInterfaceTwo {
    Product createProduct(List<Category> categoryList, Product product);
}
