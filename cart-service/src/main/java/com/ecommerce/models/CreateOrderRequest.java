package com.ecommerce.models;

import java.util.Date;
import java.util.List;

public record CreateOrderRequest(
        int userId,
        Date orderDate,
        String status,
        Double totalAmount,
        List<CartItem> items
) {}
