package com.ecommerce.products.repositories;

import com.ecommerce.products.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.arrival_status = 'New Arrival'")
    List<Product> findNewArrivals();

    @Query("SELECT p FROM Product p WHERE p.usage_category = :category")
    List<Product> findByUsageCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.season = :season")
    List<Product> findBySeason(@Param("season") String season);
}
