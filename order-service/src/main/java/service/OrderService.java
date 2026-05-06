package service;

import client.CartClient;
import domain.Order;
import domain.OrderItem;
import domain.OrderStatus;
import dto.CartResponse;
import dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import mapper.OrderMapper;
import org.springframework.stereotype.Service;
import repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CartClient cartClient;

    public OrderResponse createOrder(String cartId){
        CartResponse cart = cartClient.getCartById(cartId);

        Order order = Order.builder()
                .customerId(cart.userId())
                .orderStatus(OrderStatus.PENDING)
                .total(cart.total())
                .build();

        List<OrderItem> items = cart.items().stream()
                .map(cartItem -> OrderItem.builder()
                        .productId(cartItem.productId())
                        .quantity(cartItem.quantity())
                        .price(cartItem.price())
                        .order(order)
                        .build())
                .toList();

        order.setItems(items);

        return OrderMapper.toResponse(repository.save(order));
    }
}
