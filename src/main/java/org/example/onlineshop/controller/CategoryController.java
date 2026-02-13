package org.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
    @RequestMapping("/admin/category")
    @RequiredArgsConstructor
    public class CategoryController {

        private final CategoryService categoryService;

        @GetMapping
        public String categories(Model model) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/categories";
        }

    }

