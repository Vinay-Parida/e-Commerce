package com.example.springsecurity.factorydesign.productfactory;

import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.Product;
import com.example.springsecurity.exceptions.CategoryException;
import com.example.springsecurity.factorydesign.factoryinterface.ProductInterface;
import com.example.springsecurity.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFactory implements ProductInterface{

    @Autowired
    private CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductFactory.class);

    @Override
    public Product createProduct(String productName) {
        switch (productName) {
            case "Red Mi": {
                Product product = new Product();
                List<Category> category = categoryRepository.findAll();
                for (Category categoryName : category) {
                    if (categoryName.getName().equals("Electronics")) {
                        product.setCategory(categoryName);
                        return product;
                    }
                }
            }
            case "Shirt": {
                Product product = new Product();
                List<Category> category = categoryRepository.findAll();
                for (Category categoryName : category) {
                    if (categoryName.getName().equals("Fashion")) {
                        product.setCategory(categoryName);
                        return product;
                    }
                }
            }
            default:
                throw new CategoryException("Unsupported Product name");
        }
    }
}
