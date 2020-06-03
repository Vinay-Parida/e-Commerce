package com.example.springsecurity.factorydesign.products;

import com.example.springsecurity.dto.AddProductDTO;
import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.Product;
import com.example.springsecurity.factorydesign.factoryinterface.ProductInterface;
import com.example.springsecurity.repository.CategoryRepository;
import com.example.springsecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShirtProduct implements ProductInterface {

    @Override
    public Product createProduct(AddProductDTO addProductDTO) {
        Product product = new Product();
        product.setName(addProductDTO.getName());
        product.setBrand(addProductDTO.getBrand());
        product.setDescription(addProductDTO.getDescription());
        return product;
    }
}
