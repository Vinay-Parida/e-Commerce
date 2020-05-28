package com.example.springsecurity.controller;

import com.example.springsecurity.entity.products.Category;
import com.example.springsecurity.entity.products.ProductVariation;
import com.example.springsecurity.repository.CategoryRepository;
import com.example.springsecurity.repository.ProductRepository;
import com.example.springsecurity.repository.ProductVariationRepository;
import com.example.springsecurity.dto.ProductCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("dateNow", new Date());

        return "helloWorld";
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        return "dashboard";
    }

    @GetMapping("/categoryList")
    public String categoryNumbers(Model model) {
        List<String> categoryList = new ArrayList<>();
        List<Category> allCategory = categoryRepository.findAll();
        AtomicReference<Integer> numberOfCategories = new AtomicReference<>(0);

        allCategory.forEach(category -> {
            categoryList.add(category.getName());
            numberOfCategories.getAndSet(numberOfCategories.get() + 1);

        });

        model.addAttribute("categories", categoryList);
        model.addAttribute("listOfCategories", numberOfCategories);

        return "categoryList";
    }

    @GetMapping("/productCount")
    public String productCount(Model model) {
        List<Category> allCategory = categoryRepository.findAll();

        List<ProductCountDTO> productCountDTOList = new ArrayList<>();

        allCategory.forEach(category -> {
            ProductCountDTO productCountDTO = new ProductCountDTO();
            productCountDTO.setCategoryName(category.getName());
            productCountDTO.setProductCount(productRepository.countProductByCategoryId(category.getId()));

            productCountDTOList.add(productCountDTO);
        });

        model.addAttribute("productCount", productCountDTOList);

        return "productCount";
    }

    @GetMapping("/productVariationStock")
    public String productVariationWithStock(Model model){
        List<String> productDetails = new ArrayList<>();
        List<Integer> variationQuantity = new ArrayList<>();
        List<ProductVariation> productVariationList = productVariationRepository.findAll();
        List<ProductVariation> productVariations = new ArrayList<>();

        productVariationList.forEach(productVariation -> {
            productVariations.add(productVariation);
            productDetails.add(productVariation.getProduct().getName() + " " +
                    productVariation.getProduct().getBrand() + " : " +
                    productVariation.getMetadata());
            variationQuantity.add(productVariation.getQuantityAvailable());
        });

        model.addAttribute("productDetails", productDetails);
        model.addAttribute("stockAvailable", variationQuantity);
        model.addAttribute("productVariation", productVariations);

        return "productVariationStock";
    }
}
