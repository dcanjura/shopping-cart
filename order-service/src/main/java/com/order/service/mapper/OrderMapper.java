package com.order.service.mapper;

import com.order.service.domain.Order;
import com.order.service.dto.OrderItemResponse;
import com.order.service.dto.OrderResponse;

public class OrderMapper {

    public static OrderResponse toResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotal(),
                order.getOrderStatus(),
                order.getItems().stream()
                        .map(item -> new OrderItemResponse(item.getId(), item.getProductId(), item.getQuantity(), item.getPrice()))
                        .toList()
        );
    }
}
