package com.cart.service.mapper;

import com.cart.service.domain.Cart;
import com.cart.service.dto.CartItemResponse;
import com.cart.service.dto.CartResponse;

public class CartMapper {

    public static CartResponse toResponse(Cart cart){
        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getItems().stream()
                        .map(item -> new CartItemResponse(
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice()
                        ))
                        .toList(),
                cart.getTotal()
        );
    }
}
