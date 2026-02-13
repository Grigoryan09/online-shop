package org.example.onlineshop.service;

import org.example.onlineshop.model.Category;

import java.util.List;

public interface CategoryService {


    void save(Category category);

    List<Category> findAll();

    void deleteById(int id);

    Category findById(int id);

    void updateCategory(Category category);

    boolean categoryExists(String name);




}
