package com.order.service.dto;

public record OrderRequest(String cartId, String customerId, PaymentType paymentType) {
}
