package com.ecommerce.controllers;

import com.ecommerce.models.CreateOrderRequest;
import com.ecommerce.models.OrderResponse;
import com.ecommerce.models.Product;
import com.ecommerce.services.CustomerOrderService;
import com.ecommerce.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders/")
public class OrdersController {
    private final OrderService orderService;
    private final CustomerOrderService customerOrderService;

    public OrdersController(OrderService orderService, CustomerOrderService customerOrderService) {
        this.orderService = orderService;
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("all")
    public List<OrderResponse> getOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("top")
    public List<Product> getTopOrders(){
        return orderService.getTopOrders();
    }
    // ************ Customer Interactions ************
    @PostMapping("create")
    public String createOrder(@RequestBody CreateOrderRequest request) {
        return customerOrderService.createOrder(request);
    }
    @DeleteMapping("delete/{id}")
    public void deleteOrder(@PathVariable int id){
         customerOrderService.deleteOrder(id);
    }
}
