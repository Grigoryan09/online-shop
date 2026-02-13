package org.example.onlineshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.Product;
import org.example.onlineshop.repository.ProductRepository;
import org.example.onlineshop.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void save(Product product, Authentication auth) {
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
     return  productRepository.findProductsByCategory(id);
    }
}
