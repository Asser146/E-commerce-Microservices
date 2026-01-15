package com.ecommerce.services;


import com.ecommerce.models.CreateOrderRequest;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.repositories.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {
    private final OrdersRepository ordersRepository;

    public CustomerOrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Transactional
    public String createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.userId());
        order.setOrderDate(request.orderDate());
        order.setStatus(request.status());
        order.setTotalAmount(request.totalAmount());

        List<OrderItem> items = request.items().stream()
                .map(reqItem -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(reqItem.productId());
                    item.setName(reqItem.name());
                    item.setQuantity(reqItem.quantity());
                    item.setPrice(reqItem.price());
                    item.setOrder(order);
                    return item;
                })
                .toList();

        order.setOrderItemList(items);

        Order saved = ordersRepository.save(order);

        return "Success";
    }


    @Transactional
    public void deleteOrder(int id) {

        if (!ordersRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found: " + id);
        }

        ordersRepository.deleteById(id);
    }

}
