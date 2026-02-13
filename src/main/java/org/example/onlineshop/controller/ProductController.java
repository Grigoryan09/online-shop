package org.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Product;
import org.example.onlineshop.service.CategoryService;
import org.example.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/user/product/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/category/{id}/products")
    public String getProductsByCategory(@PathVariable int id, Model model) {
        List<Product> products = productService.findByCategoryId(id);
        model.addAttribute("products", products);
        model.addAttribute("comments", Collections.emptyList());

        return "category-products";
    }


}
