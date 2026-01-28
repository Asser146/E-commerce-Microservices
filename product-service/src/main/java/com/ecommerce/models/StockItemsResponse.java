package com.ecommerce.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
public class StockItemsResponse {
    private int status;
    private String message;
    private List<String> details;
}
