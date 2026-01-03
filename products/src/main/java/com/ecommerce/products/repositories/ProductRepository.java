package com.ecommerce.products.repositories;

import com.ecommerce.products.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.arrival_status = 'New Arrival'")
    List<Product> findNewArrivals();

    @Query("SELECT p FROM Product p WHERE p.usage_category = :category")
    Page<Product> findByUsageCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.season = :season")
    Page<Product> findBySeason(@Param("season") String season, Pageable pageable);

}
