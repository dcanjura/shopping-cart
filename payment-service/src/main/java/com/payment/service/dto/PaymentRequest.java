package com.payment.service.dto;

public record PaymentRequest(String paymentType, Double amount) {
}
