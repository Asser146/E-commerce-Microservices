package com.ecommerce.products.services;

import com.ecommerce.products.models.Product;
import com.ecommerce.products.repositories.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProductServices {

    private final ProductRepository repository;
    private final JdbcTemplate jdbcTemplate;


    public ProductServices(ProductRepository repository,JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
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


    public List<String> getProductColors(int id) {
        String sql = "SELECT pc.color " +
                "FROM  products_colors pc " +
                "WHERE pc.product_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, id);
    }
    public List<String> getProductSizes(int id) {
        String sql = "SELECT ps.size " +
                "FROM  products_sizes ps " +
                "WHERE ps.product_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, id);
    }

    public List<String> getAllColors() {
        String sql = "SELECT distinct pc.color " +
                "FROM  products_colors pc ";
        return jdbcTemplate.queryForList(sql, String.class);
    }
    public List<String> getAllSizes() {
        String sql = "SELECT distinct ps.size " +
                "FROM  products_sizes ps ";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
