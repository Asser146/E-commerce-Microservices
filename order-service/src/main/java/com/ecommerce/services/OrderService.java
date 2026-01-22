package com.ecommerce.services;

import com.ecommerce.models.OrderItemResponse;
import com.ecommerce.models.OrderResponse;
import com.ecommerce.models.Product;
import com.ecommerce.repositories.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final WebClient.Builder webClient;


    public List<OrderResponse> getAllOrders() {
        return ordersRepository.findAll().stream().map(order -> {
            OrderResponse dto = new OrderResponse();
            dto.setId(order.getId());
            dto.setUserId(order.getUserId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStatus(order.getStatus());
            dto.setTotalAmount(order.getTotalAmount());

            List<OrderItemResponse> items = order.getOrderItemList().stream().map(item -> {
                OrderItemResponse i = new OrderItemResponse();
                i.setProductId(item.getProductId());
                i.setName(item.getName());
                i.setQuantity(item.getQuantity());
                i.setPrice(item.getPrice());
                return i;
            }).toList();

            dto.setItems(items);
            return dto;
        }).toList();
    }

    @CircuitBreaker(name = "productBreaker",
    fallbackMethod = "productBreakerFallback")
    public List<Product> getTopOrders() {

        List<Integer> productIds =
                ordersRepository.getTopOrders(
                        Sort.by(Sort.Direction.DESC, "quantity")
                );

        if (productIds.isEmpty()) {
            System.out.println(productIds);
            return List.of();
        }
        String idsParam = productIds.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");


        return  webClient.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")        // optional
                        .host("product-service") // just service name
                        .path("/api/top")
                        .queryParam("ids", idsParam)
                        .build())
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
        // blocking since your service is not reactive
    }
    public List<Product> productBreakerFallback(Exception e) {
        List<Product> dummyProducts = new ArrayList<>();

        dummyProducts.add(new Product(1, "Male", "Clothing", "Shirts", "Summer", "Casual",
                "Dummy Shirt 1", 10, "New Arrival", 29.99, "/images/shirt1.jpg", 4.5));

        dummyProducts.add(new Product(2, "Female", "Clothing", "Dresses", "Spring", "Formal",
                "Dummy Dress 2", 5, "New Arrival", 49.99, "/images/dress2.jpg", 4.0));

        dummyProducts.add(new Product(3, "Male", "Clothing", "Pants", "Winter", "Casual",
                "Dummy Pants 3", 8, "Coming Soon", 39.99, "/images/pants3.jpg", 3.8));

        return dummyProducts;
    }


}
