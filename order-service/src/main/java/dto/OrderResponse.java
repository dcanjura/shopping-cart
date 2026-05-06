package dto;

import domain.OrderStatus;

import java.util.List;

public record OrderResponse(Long id, String customerId, Double total, OrderStatus orderStatus, List<OrderItemResponse> items) {
}
