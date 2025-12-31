package com.ecommerce.products.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "stock")
    private int stock;
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
    @Column(name = "arrival_status")
    private String arrival_status;

    public Product(int id, int stock, String gender, String master_category, String sub_category, String season, String usage_category, String title, String arrival_status) {
        this.id = id;
        this.stock = stock;
        this.gender = gender;
        this.master_category = master_category;
        this.sub_category = sub_category;
        this.season = season;
        this.usage_category = usage_category;
        this.title = title;
        this.arrival_status = arrival_status;
    }

    public Product() {
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
