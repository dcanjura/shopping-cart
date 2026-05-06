package com.order.service.client;

import com.order.service.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CartClient {

    private final RestTemplate restTemplate;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    public CartResponse getCartById(String cartId){
        return restTemplate.getForObject(
                cartServiceUrl + cartId,
                CartResponse.class
        );
    }
}
