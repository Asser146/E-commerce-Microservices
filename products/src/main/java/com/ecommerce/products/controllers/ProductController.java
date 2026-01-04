package com.ecommerce.products.controllers;

import com.ecommerce.products.models.Product;
import com.ecommerce.products.services.ProductServices;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")

public class ProductController {
    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @GetMapping("/all")
    public PagedModel<Product> getProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productServices.getAllProducts(pageable);
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
    public PagedModel<Product> getProductsByUsage(@PathVariable String usage,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page,size);
        return productServices.getByUsageCategory(usage,pageable);
    }

    @GetMapping("/season/{season}")
    public PagedModel<Product> getProductsBySeason(@PathVariable String season ,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page,size);
        return productServices.getBySeason(season,pageable);
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
    @GetMapping("/filter")
    public PagedModel<Product> getProductsByFilter(
            @RequestParam(value = "colors", defaultValue = "") List<String> colors,
            @RequestParam(value = "sizes", defaultValue = "") List<String> sizes,
            @RequestParam(value = "sub_categories", defaultValue = "") List<String> subCategories,
            @RequestParam(value = "price_range", defaultValue = "") List<Double> priceRange,
            @RequestParam(value = "styles", defaultValue = "") List<String> styles,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productServices.getByFilter(colors, sizes,subCategories,priceRange,styles, pageable);
    }

}

