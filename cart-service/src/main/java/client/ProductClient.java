package client;

import dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    private static final String PRODUCT_SERVICE_URL = "http://localhost:8082/products/";

    public ProductResponse getProductById(String productId){
        return  restTemplate.getForObject(
                PRODUCT_SERVICE_URL + productId,
                ProductResponse.class
        );
    }
}
