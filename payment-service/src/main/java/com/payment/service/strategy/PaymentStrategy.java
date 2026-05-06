package com.payment.service.strategy;

public interface PaymentStrategy {

    boolean processPayment(Double amount);
}
