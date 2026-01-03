package com.ecommerce.products.services;

import com.ecommerce.products.models.Product;
import com.ecommerce.products.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServices {

    private final ProductRepository repository;
    private final JdbcTemplate jdbcTemplate;


    public ProductServices(ProductRepository repository,JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public PagedModel<Product> getAllProducts(Pageable pageable) {

        Page<Product> products =repository.findAll(pageable);
        return new PagedModel<>(products);
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public List<Product> getNewArrivals() {
        return repository.findNewArrivals();
    }
    public PagedModel<Product> getByUsageCategory(String category,Pageable pageable) {
        Page<Product> products=repository.findByUsageCategory(category,pageable);
        return new PagedModel<>(products);
    }
    public PagedModel<Product> getBySeason(String season, Pageable pageable) {
        Page<Product> products= repository.findBySeason(season,pageable);
        return new PagedModel<>(products);
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

    public PagedModel<Product> getByFilter(List<String> colors, List<String> sizes, Pageable pageable) {
        // Base SQL for fetching products
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT p.* " +
                        "FROM products p " +
                        "JOIN products_colors pc ON p.id = pc.product_id " +
                        "JOIN products_sizes ps ON p.id = ps.product_id " +
                        "WHERE 1=1 "
        );
        // Base SQL for counting total elements
        StringBuilder countSql = new StringBuilder(
                "SELECT COUNT(DISTINCT p.id) " +
                        "FROM products p " +
                        "JOIN products_colors pc ON p.id = pc.product_id " +
                        "JOIN products_sizes ps ON p.id = ps.product_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Add color filter if list is not empty
        if (colors != null && !colors.isEmpty()) {
            String placeholders = colors.stream().map(c -> "?").collect(Collectors.joining(","));
            sql.append(" AND pc.color IN (" + placeholders + ") ");
            countSql.append(" AND pc.color IN (" + placeholders + ") ");
            params.addAll(colors);
        }

        // Add size filter if list is not empty
        if (sizes != null && !sizes.isEmpty()) {
            String placeholders = sizes.stream().map(s -> "?").collect(Collectors.joining(","));
            sql.append(" AND ps.size IN (" + placeholders + ") ");
            countSql.append(" AND ps.size IN (" + placeholders + ") ");
            params.addAll(sizes);
        }

        // Pagination for product query
        sql.append(" LIMIT ? OFFSET ? ");
        params.add(pageable.getPageSize());
        params.add(pageable.getPageNumber() * pageable.getPageSize());


        // Execute product query
        List<Product> products = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                new BeanPropertyRowMapper<>(Product.class)
        );

        // Execute count query to get total elements
        Long totalElements = jdbcTemplate.queryForObject(
                countSql.toString(),
                params.subList(0, params.size() - 2).toArray(), // exclude LIMIT/OFFSET
                Long.class
        );


        // Wrap in Page and then PagedModel
        Page<Product> page = new PageImpl<>(products, pageable, totalElements);
        return new PagedModel<>(page);
    }



}
