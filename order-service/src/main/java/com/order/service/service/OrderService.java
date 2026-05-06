package com.order.service.service;

import com.order.service.client.CartClient;
import com.order.service.client.PaymentClient;
import com.order.service.client.ProductClient;
import com.order.service.domain.Order;
import com.order.service.domain.OrderItem;
import com.order.service.dto.OrderStatus;
import com.order.service.dto.CartResponse;
import com.order.service.dto.OrderRequest;
import com.order.service.dto.OrderResponse;
import com.order.service.dto.PaymentRequest;
import com.order.service.exception.BadRequestException;
import com.order.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.order.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import com.order.service.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CartClient cartClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;

    public OrderResponse createOrder(OrderRequest request){
        if(request.cartId() == null || request.cartId().isBlank()){
            throw new BadRequestException("cartId is required");
        }

        CartResponse cart = cartClient.getCartById(request.cartId());

        Order order = Order.builder()
                .customerId(request.customerId())
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

        boolean success = paymentClient.processPayment(new PaymentRequest(request.paymentType().name(), order.getTotal()));

        if(success){
            for (OrderItem item : order.getItems()){
                productClient.reduceStock(item.getProductId(), item.getQuantity());
            }

            order.setOrderStatus(OrderStatus.PAID);
        }else {
            order.setOrderStatus(OrderStatus.FAILED);
        }

        return OrderMapper.toResponse(repository.save(order));
    }
}
