package com.example.SpringSecurity.thymeleaf.controller;

import com.example.SpringSecurity.entity.products.Category;
import com.example.SpringSecurity.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/admin")
public class CategoryListController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categoryList")
    public String categoryNumbers(Model model){
        List<String> categoryList = new ArrayList<>();
        List<Category> allCategory = categoryRepository.findAll();
        AtomicReference<Integer> numberOfCategories = new AtomicReference<>(0);

        allCategory.forEach(category -> {
            categoryList.add(category.getName());
            numberOfCategories.getAndSet(numberOfCategories.get() + 1);

        });

        model.addAttribute("categories", categoryList);
        model.addAttribute("listOfCategories",numberOfCategories);

        return "categoryList";

    }

}
