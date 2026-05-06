package com.order.service.dto;

import java.util.List;

public record CartResponse(Long id, String userId, List<CartItemResponse> items, Double total) {
}
