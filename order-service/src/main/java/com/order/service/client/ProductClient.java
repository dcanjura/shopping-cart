package com.order.service.client;

import com.order.service.dto.ReduceStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public void reduceStock(String productId, Integer quantity){
        restTemplate.postForObject(
                productServiceUrl + "/products/reduce-stock",
                new ReduceStockRequest(productId, quantity),
                Void.class
        );
    }
}
