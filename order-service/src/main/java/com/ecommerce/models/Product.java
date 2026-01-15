package com.ecommerce.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private int id;
    private String gender;
    private String master_category;
    private String sub_category;
    private String season;
    private String usage_category;
    private String title;
    private int stock;
    private String arrival_status;
    private double price;
    private String image_path;
    private double rating;
}
