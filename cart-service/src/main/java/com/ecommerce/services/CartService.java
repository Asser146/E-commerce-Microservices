package com.ecommerce.services;


import com.ecommerce.models.*;
import com.ecommerce.models.*;
import com.ecommerce.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final WebClient.Builder webClient;

    public Cart getOrCreateActiveCart(int userId) {

        Optional<Cart> existingCart =
                cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);

        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setStatus(CartStatus.ACTIVE);

        return cartRepository.save(newCart);
    }

    public Cart addItemToCart(
            int userId,
            CartItemRequest itemRequest

    ) {
        Cart cart = getOrCreateActiveCart(userId);

        for (CartItem item : cart.getItems()) {
            if (item.getProductId() == itemRequest.getProductId()) {
                item.setQuantity(item.getQuantity() + itemRequest.getQuantity());
                return cartRepository.save(cart);
            }
        }

        CartItem item = CartItem.builder()
                .productId(itemRequest.getProductId())
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .quantity(itemRequest.getQuantity())
                .build();
        cart.addItem(item);
        return cartRepository.save(cart);
    }

    public String updateCartItems(int userId, List<CartItemRequest> cartItems) {
        Cart cart = getOrCreateActiveCart(userId);
        for (CartItemRequest req : cartItems) {
            Optional<CartItem> existing = cart.getItems().stream()
                    .filter(i -> i.getProductId() == req.getProductId())
                    .findFirst();
            if (req.getQuantity() <= 0) {
                existing.ifPresent(cart::removeItem);
            } else {
                existing.ifPresent(cartItem -> cartItem.setQuantity(req.getQuantity()));
            }
        }
        if (cart.getItems().isEmpty()) {
            cartRepository.delete(cart);
            return "Cart Deleted";
        } else {
             cartRepository.save(cart);
            return "Cart Updated";
        }
    }

    public ActiveCartResponse getActiveCart(int userId) {
        Optional<Cart> existingCart =
                cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        return existingCart.map(cart -> new ActiveCartResponse(true, cart)).orElseGet(() -> new ActiveCartResponse(false, null));
    }
    public String checkout(int userId) {
        Optional<Cart> existingCart =
                cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        if (existingCart.isPresent()){
            Cart cart = existingCart.get();
            double totalAmount = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                    cart.getUserId(),
                    new Date(),
                    "CREATED",
                    totalAmount,
                    cart.getItems()
            );
            // Send POST request to Order service
            String response = webClient.build().post()
                    .uri("http://orders/api/orders/create")
                    .bodyValue(createOrderRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            // Mark cart as CHECKED_OUT
            cart.setStatus(CartStatus.CHECKED_OUT);
            cart.getItems().clear();
            cartRepository.save(cart);

            return response;
        }else{
            return "Cart Error";
        }
    }


}
