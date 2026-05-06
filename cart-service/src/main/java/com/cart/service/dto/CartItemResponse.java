package com.cart.service.dto;

public record CartItemResponse(String productId, Integer quantity, Double price) {
}
