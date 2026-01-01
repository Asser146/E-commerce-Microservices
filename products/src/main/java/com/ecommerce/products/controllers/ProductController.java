package com.ecommerce.products.controllers;

import com.ecommerce.products.models.Product;
import com.ecommerce.products.services.ProductServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")

public class ProductController {
    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @GetMapping("/all")
    public List<Product> getProducts(){
        return productServices.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product getProducts(@PathVariable long id){
        return productServices.getProductById(id);
    }
    @GetMapping("/new")
        public  List<Product> getNewArrivals   ()   {
    return productServices.getNewArrivals();
    }

    @GetMapping("/usage/{usage}")
    public List<Product> getProductsByUsage(@PathVariable String usage){
        return productServices.getByUsageCategory(usage);
    }

    @GetMapping("/season/{season}")
    public List<Product> getProductsBySeason(@PathVariable String season){
        return productServices.getBySeason(season);
    }
    @GetMapping("/colors")
    public List<String> getColors(){
        return productServices.getAllColors();
    }
    @GetMapping("/colors/{id}")
    public List<String> getProductColors(@PathVariable int id){
        return productServices.getProductColors(id);
    }
    @GetMapping("/sizes")
    public List<String> getSizes(){
        return productServices.getAllSizes();
    }
    @GetMapping("/sizes/{id}")
    public List<String> getProductSizes(@PathVariable int id){
        return productServices.getProductSizes(id);
    }
}
