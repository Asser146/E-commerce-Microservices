package com.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveCartResponse {
    private boolean hasActiveCart;
    private Cart cart;     // null if none
}
