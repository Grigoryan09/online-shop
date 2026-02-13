package org.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Category;
import org.example.onlineshop.model.Product;
import org.example.onlineshop.service.CategoryService;
import org.example.onlineshop.service.ProductService;
import org.example.onlineshop.service.UserService;
import org.example.onlineshop.service.security.SpringUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @Value("${online.shop.upload.image.directory,path}")
    private String imageDirectoryPath;

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin/home";
    }

    // ========== CATEGORY MANAGEMENT ==========
    @GetMapping("/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin/categories";
    }

    @PostMapping("/categories/create")
    public String createCategory(@ModelAttribute Category category) {
        if (!categoryService.categoryExists(category.getName())) {
            categoryService.save(category);
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/edit-category";
    }

    @PostMapping("/categories/update")
    public String updateCategory(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    // ========== PRODUCT MANAGEMENT ==========
    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("newProduct", new Product());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String createProduct(@AuthenticationPrincipal SpringUser springUser, ModelMap modelMap){
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        modelMap.addAttribute("categories",categoryService.findAll());
        modelMap.addAttribute("product", new Product());
        return "admin/product-form";
    }

    @PostMapping("products/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam MultipartFile image,
                             Authentication auth) throws IOException {

        if (!image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            image.transferTo(new File(imageDirectoryPath + fileName));
            product.setPictureName(fileName);
        }

        productService.save(product, auth);
        return "redirect:/admin/home";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/edit-product";
    }

    @PostMapping("products/update")
    public String updateProduct(@ModelAttribute Product product,
                                @RequestParam(required = false) MultipartFile image,
                                Authentication auth) throws IOException {
        Product existingProduct = productService.findById(product.getId());

        if (existingProduct != null) {
            existingProduct.setTitle(product.getTitle());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());

            if (image != null && !image.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                image.transferTo(new File(imageDirectoryPath + fileName));
                existingProduct.setPictureName(fileName);
            }

            productService.save(existingProduct, auth);
        }

        return "redirect:/admin/products";
    }


    @GetMapping("products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    // ========== USER MANAGEMENT ==========
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String addUserForm() {
        return "admin/add-user";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String role) {
        org.example.onlineshop.model.User user = new org.example.onlineshop.model.User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(org.example.onlineshop.model.UserRole.valueOf(role));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
