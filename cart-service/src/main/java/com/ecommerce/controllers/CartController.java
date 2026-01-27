package com.ecommerce.controllers;


import com.ecommerce.models.ActiveCartResponse;
import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItemRequest;
import com.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @GetMapping("/active")
    public List<Cart> getActiveCarts()
    {
        return cartService.getActiveCarts();
    }

    @GetMapping("/active/{userId}")
    public ActiveCartResponse getUserActiveCart(@PathVariable int userId) {
        return cartService.getUserActiveCart(userId);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable int userId,
            @RequestBody CartItemRequest request
    ) {
        Cart cart = cartService.addItemToCart(
                userId,
                request
        );
        return ResponseEntity.ok(cart);
    }
    @PutMapping("/{userId}/items/update")
    public String updateCart(
            @PathVariable int userId,
            @RequestBody List<CartItemRequest> request
    ) {

        return cartService.updateCartItems(
                userId,
                request
        );
    }
@PostMapping("{userId}/checkout")
public String checkout(@PathVariable int userId) {
    return cartService.checkout(userId);
}
}
