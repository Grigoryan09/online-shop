package org.example.onlineshop.repository;

import org.example.onlineshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);
}
