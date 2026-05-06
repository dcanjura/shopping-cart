package com.order.service.client;

import com.order.service.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    public boolean processPayment(PaymentRequest request){
        String response = restTemplate.postForObject(
                paymentServiceUrl,
                request,
                String.class
        );
        return "SUCCESS".equalsIgnoreCase(response);
    }
}
