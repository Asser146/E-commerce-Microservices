package com.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CartItemRequest {
    private int productId;
    private String name;
    private int quantity;
    private double price;
}
