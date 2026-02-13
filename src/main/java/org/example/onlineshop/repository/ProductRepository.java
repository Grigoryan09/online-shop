package org.example.onlineshop.repository;

import org.example.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategory(@Param("categoryId") int categoryId);
}
