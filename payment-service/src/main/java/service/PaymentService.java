package service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import strategy.PaymentStrategy;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Map<String, PaymentStrategy> map;

    public boolean processPayment(String paymentType, Double amount){
        PaymentStrategy strategy = map.get(paymentType);

        if(strategy == null){
            throw new RuntimeException("Invalid payment type");
        }

        return strategy.processPayment(amount);
    }
}
