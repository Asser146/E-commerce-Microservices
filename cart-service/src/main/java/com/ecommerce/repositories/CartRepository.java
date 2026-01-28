package com.ecommerce.repositories;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserIdAndStatus(int userId, CartStatus status);
    List<Cart> findByStatus(CartStatus status);
}
