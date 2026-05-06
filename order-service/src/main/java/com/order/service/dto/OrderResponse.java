package com.order.service.dto;

import java.util.List;

public record OrderResponse(Long id, String customerId, Double total, OrderStatus status, List<OrderItemResponse> items) {
}
