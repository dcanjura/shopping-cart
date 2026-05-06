package mapper;

import domain.Cart;
import dto.CartItemResponse;
import dto.CartResponse;

public class CartMapper {

    public static CartResponse toResponse(Cart cart){
        cart.setTotal(
                cart.getItems().stream()
                        .mapToDouble(i -> i.getPrice() * i.getQuantity())
                        .sum()
        );

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
