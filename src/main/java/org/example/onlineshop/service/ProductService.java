package org.example.onlineshop.service;

import org.example.onlineshop.model.Product;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductService {

    void save(Product product, Authentication auth);

    List<Product> findAll();

    Product findById(int id);

    void deleteById(int id);

    List<Product> findByCategoryId(int id);
}
