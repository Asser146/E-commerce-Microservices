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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServices {

    private final ProductRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private  final Map<String, String> SORT_COLUMN_MAP = Map.of(
            "price", "p.price",
            "rating", "p.rating"
    );


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

    public PagedModel<Product> getByFilter(List<String> colors, List<String> sizes,List<String> subCategories,List<Double> pricesRange,List<String> dressStyles, Pageable pageable) {
        // Base SQL for fetching products
        StringBuilder sql = new StringBuilder(
                "SELECT  p.* " +
                        "FROM products p " +
                        "JOIN products_colors pc ON p.id = pc.product_id " +
                        "JOIN products_sizes ps ON p.id = ps.product_id " +
                        "WHERE 1=1 "
        );
        // Base SQL for counting total elements
        StringBuilder countSql = new StringBuilder(
                "SELECT COUNT(p.id) " +
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

        if (subCategories != null && !subCategories.isEmpty()) {
            String placeholders = subCategories.stream().map(s -> "?").collect(Collectors.joining(","));
            sql.append(" AND p.sub_category IN (" + placeholders + ") ");
            countSql.append(" AND p.sub_category IN (" + placeholders + ") ");
            params.addAll(subCategories);
        }
        if (pricesRange != null && pricesRange.size() == 2) {
            sql.append(" AND p.price BETWEEN ? AND ? ");
            countSql.append(" AND p.price BETWEEN ? AND ? ");
            params.add(pricesRange.get(0));
            params.add(pricesRange.get(1));
        }

        if (dressStyles != null && !dressStyles.isEmpty()) {
            String placeholders = dressStyles.stream().map(s -> "?").collect(Collectors.joining(","));
            sql.append(" AND p.usage_category IN (" + placeholders + ") ");
            countSql.append(" AND p.usage_category IN (" + placeholders + ") ");
            params.addAll(dressStyles);
        }

        if (pageable.getSort().isSorted()) {

            sql.append(" ORDER BY ");
            List<String> orderClauses = new ArrayList<>();

            pageable.getSort().forEach(order -> {
                String column = SORT_COLUMN_MAP.get(order.getProperty());
                if (column != null) {
                    orderClauses.add(column + " " + order.getDirection().name());
                }
            });

            if (!orderClauses.isEmpty()) {
                sql.append(String.join(", ", orderClauses));
            } else {
                sql.append(" p.rating DESC ");
            }

        } else {
            sql.append(" ORDER BY p.rating DESC ");
        }

        // Pagination for product query
        sql.append(" LIMIT ? OFFSET ? ");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());


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
