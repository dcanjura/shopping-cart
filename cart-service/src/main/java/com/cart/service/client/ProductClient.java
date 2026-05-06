package com.cart.service.client;

import com.cart.service.dto.ProductResponse;
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

    public ProductResponse getProductById(String productId){
        try {
            return  restTemplate.getForObject(
                    productServiceUrl + productId,
                    ProductResponse.class
            );
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}
