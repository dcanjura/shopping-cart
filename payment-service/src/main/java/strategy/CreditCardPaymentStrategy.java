package strategy;

import org.springframework.stereotype.Component;

@Component("CARD")
public class CreditCardPaymentStrategy implements PaymentStrategy{

    @Override
    public boolean processPayment(Double amount){
        //Simple logic simulated
        return amount < 1000;
    }
}
