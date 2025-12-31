package com.ecommerce.products.services;

import com.ecommerce.products.models.Product;
import com.ecommerce.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServices {

    private final ProductRepository repository;

    public ProductServices(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public List<Product> getNewArrivals() {
        return repository.findNewArrivals();
    }
    public List<Product> getByUsageCategory(String category) {
        return repository.findByUsageCategory(category);
    }
    public List<Product> getBySeason(String season) {
        return repository.findBySeason(season);
    }
}
