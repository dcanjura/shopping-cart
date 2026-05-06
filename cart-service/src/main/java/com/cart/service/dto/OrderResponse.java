package com.cart.service.dto;

public record OrderResponse(Long id, String customerId, Double total, String status) {
}
