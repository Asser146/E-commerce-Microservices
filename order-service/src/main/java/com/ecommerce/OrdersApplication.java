package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OrdersApplication {
    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder(){
        return WebClient.builder();
    }
	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

}
