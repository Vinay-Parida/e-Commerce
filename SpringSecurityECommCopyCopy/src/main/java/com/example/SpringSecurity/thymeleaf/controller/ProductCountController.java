package com.example.SpringSecurity.thymeleaf.controller;

import com.example.SpringSecurity.entity.products.Category;
import com.example.SpringSecurity.repository.CategoryRepository;
import com.example.SpringSecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductCountController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/productCount")
    public String productCount(Model model){
        List<String> categoryList = new ArrayList<>();
        List<Long> productCountList = new ArrayList<>();
        List<Category> allCategory = categoryRepository.findAll();

        allCategory.forEach(category -> {
            categoryList.add(category.getName());
            productCountList.add(productRepository.countProductByCategoryId(category.getId()));

        });

        model.addAttribute("categories", categoryList);
            model.addAttribute("productsPerCategory", productCountList);

        return "productCount";
    }
}
