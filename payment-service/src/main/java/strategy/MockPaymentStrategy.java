package strategy;

import org.springframework.stereotype.Component;

@Component("MOCK")
public class MockPaymentStrategy implements PaymentStrategy{

    @Override
    public boolean processPayment(Double amount){
        //Always success (mock)
        return true;
    }
}
