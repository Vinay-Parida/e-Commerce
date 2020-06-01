package com.example.springsecurity.factorydesign.controller;

import com.example.springsecurity.dto.AddProductDTO;
import com.example.springsecurity.entity.products.Product;
import com.example.springsecurity.factorydesign.productfactory.ProductFactory;
import com.example.springsecurity.repository.ProductRepository;
import com.jfilter.filter.FieldFilterSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductFactoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductFactory productFactory;

    @FieldFilterSetting(className = AddProductDTO.class, fields = {"isCancellable","isReturnable"})
    @PostMapping("/addProduct")
    public String addProduct(@RequestBody AddProductDTO addProductDTO){
        //Service
        Product product = this.productFactory.createProduct(addProductDTO.getName());
        product.setName(addProductDTO.getName());
        product.setBrand(addProductDTO.getBrand());
        product.setDescription(addProductDTO.getDescription());
        product.setBrand(addProductDTO.getBrand());
        productRepository.save(product);

        return "Product added successfully";
    }
}
