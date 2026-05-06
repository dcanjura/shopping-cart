package com.cart.service.client;

import com.cart.service.dto.OrderRequest;import com.cart.service.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public OrderResponse createOrder(OrderRequest request){
        return restTemplate.postForObject(
                orderServiceUrl + "/save",
                request,
                OrderResponse.class
        );
    }
}
