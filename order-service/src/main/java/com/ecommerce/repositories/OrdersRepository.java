package com.ecommerce.repositories;

import com.ecommerce.models.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT DISTINCT i.productId FROM OrderItem i")
    List<Integer> getTopOrders(Sort sort);

}
