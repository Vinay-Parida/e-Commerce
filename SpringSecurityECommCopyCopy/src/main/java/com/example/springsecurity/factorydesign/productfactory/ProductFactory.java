package com.example.springsecurity.factorydesign.productfactory;

import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.Product;
import com.example.springsecurity.exceptions.CategoryException;
import com.example.springsecurity.factorydesign.factoryinterface.ProductInterface;
import com.example.springsecurity.factorydesign.products.RedMiProduct;
import com.example.springsecurity.factorydesign.products.ShirtProduct;
import com.example.springsecurity.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFactory {

    @Autowired
    private CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductFactory.class);

    public ProductInterface createNewProduct(String productName) {
        switch (productName) {
            case "Red Mi":
                return new RedMiProduct();

            case "Shirt":
                return new ShirtProduct();

            default:
                throw new CategoryException("Unsupported Product name");

        }
    }
}
