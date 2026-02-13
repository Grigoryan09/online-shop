package org.example.onlineshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Category;
import org.example.onlineshop.repository.CategoryRepository;
import org.example.onlineshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public boolean categoryExists(String name) {
        return  categoryRepository.existsByName(name);
    }
}
