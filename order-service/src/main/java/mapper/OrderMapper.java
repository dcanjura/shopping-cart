package mapper;

import domain.Order;
import dto.OrderItemResponse;
import dto.OrderResponse;

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
