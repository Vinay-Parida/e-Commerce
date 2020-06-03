package com.example.springsecurity.factorydesign.controller;

import com.example.springsecurity.dto.AddProductDTO;
import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.Product;
import com.example.springsecurity.factorydesign.factoryinterface.ProductInterface;
import com.example.springsecurity.factorydesign.productfactory.ProductFactory;
import com.example.springsecurity.repository.CategoryRepository;
import com.example.springsecurity.repository.ProductRepository;
import com.jfilter.filter.FieldFilterSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductFactoryController {

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private ProductRepository productRepository;

    @FieldFilterSetting(className = AddProductDTO.class, fields = {"isCancellable","isReturnable"})
    @PostMapping("/addProduct")
    public String addProduct(@RequestBody AddProductDTO addProductDTO){
        Product product = this.productFactory.createNewProduct(addProductDTO.getName()).createProduct(addProductDTO);
        productRepository.save(product);

        return "Product added successfully";
    }
}
