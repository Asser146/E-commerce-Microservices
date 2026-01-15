package com.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
        status = CartStatus.ACTIVE;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = new Date();
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

}
