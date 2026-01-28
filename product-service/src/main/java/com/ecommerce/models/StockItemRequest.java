package com.ecommerce.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class StockItemRequest {
    private int productId;
    private int quantity;
}
