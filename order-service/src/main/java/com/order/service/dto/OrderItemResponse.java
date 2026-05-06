package com.order.service.dto;

public record OrderItemResponse(Long id, String productId, Integer quantity, Double price) {
}
