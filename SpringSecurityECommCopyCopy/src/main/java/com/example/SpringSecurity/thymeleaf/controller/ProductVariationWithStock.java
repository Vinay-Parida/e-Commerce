package com.example.SpringSecurity.thymeleaf.controller;

import com.example.SpringSecurity.entity.products.ProductVariation;
import com.example.SpringSecurity.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductVariationWithStock {

    @Autowired
    private ProductVariationRepository productVariationRepository;

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
