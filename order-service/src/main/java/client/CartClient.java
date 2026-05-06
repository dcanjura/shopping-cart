package client;

import dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CartClient {

    private RestTemplate restTemplate;

    private static final String CART_SERVICE_URL = "http://localhost:8080/cart/";

    public CartResponse getCartById(String cartId){
        return restTemplate.getForObject(
                CART_SERVICE_URL + cartId,
                CartResponse.class
        );
    }
}
