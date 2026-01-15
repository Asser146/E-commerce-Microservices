package com.ecommerce.models;



public record CreateOrderItemRequest(
        int productId,
        String name,
        int quantity,
        double price
) {}
