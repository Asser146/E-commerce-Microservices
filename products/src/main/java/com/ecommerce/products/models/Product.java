package com.ecommerce.products.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "gender")
    private String gender;
    @Column(name = "master_category")
    private String master_category;
    @Column(name = "sub_category")
    private String sub_category;
    @Column(name = "season")
    private String season;
    @Column(name = "usage_category")
    private String usage_category;
    @Column(name = "title")
    private String title;
    @Column(name = "stock")
    private int stock;
    @Column(name = "arrival_status")
    private String arrival_status;
    @Column(name = "price")
    private double price;
    @Column(name = "image_path")
    private String image_path;
    @Column(name = "rating")
    private double rating;

    public Product(int id, String gender, String master_category, String sub_category, String season, String usage_category, String title, int stock, String arrival_status, double price, String image_path, double rating) {
        this.id = id;
        this.gender = gender;
        this.master_category = master_category;
        this.sub_category = sub_category;
        this.season = season;
        this.usage_category = usage_category;
        this.title = title;
        this.stock = stock;
        this.arrival_status = arrival_status;
        this.price = price;
        this.image_path = image_path;
        this.rating = rating;
    }

    public Product() {
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaster_category() {
        return master_category;
    }

    public void setMaster_category(String master_category) {
        this.master_category = master_category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getUsage_category() {
        return usage_category;
    }

    public void setUsage_category(String usage_category) {
        this.usage_category = usage_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArrival_status() {
        return arrival_status;
    }

    public void setArrival_status(String arrival_status) {
        this.arrival_status = arrival_status;
    }
}
