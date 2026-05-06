package service;

import client.ProductClient;
import domain.Cart;
import domain.Item;
import dto.*;
import lombok.RequiredArgsConstructor;
import mapper.CartMapper;
import org.springframework.stereotype.Service;
import repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final ProductClient productClient;

    public CartResponse create(CreateCartRequest request){
        return CartMapper.toResponse(repository.createCart(request.userId()));
    }

    public CartResponse getByUser(CreateCartRequest request){
        return CartMapper.toResponse(repository.getCartByUser(request.userId()));
    }

    public CartResponse addItem(AddItemRequest request, Long cartId){
        Cart cart = repository.getCartById(cartId);
        Optional<Item> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProductId().equalsIgnoreCase(request.productId()))
                .findFirst();

        itemOpt.ifPresentOrElse(
                i -> {
                    i.setQuantity(i.getQuantity() + request.quantity());
                },
                () -> {
                    ProductResponse product = productClient.getProductById(request.productId());
                    Item newItem = Item.builder()
                            .productId(request.productId())
                            .quantity(request.quantity())
                            .price(product.price())
                            .cart(cart)
                            .build();

                    cart.getItems().add(newItem);
                }
        );

        cart.setTotal(calculateTotal(cart));

        return CartMapper.toResponse(repository.save(cart));
    }

    public CartResponse removeItem(Long cartId, String productId){
        Cart cart = repository.getCartById(cartId);

        cart.getItems().removeIf(item -> (item.getProductId().equalsIgnoreCase(productId)));

        cart.setTotal(calculateTotal(cart));

        return CartMapper.toResponse(repository.save(cart));
    }

    public CartResponse updateItem(UpdateItemRequest request, Long cartId, String productId){
        Cart cart = repository.getCartById(cartId);

        Optional<Item> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProductId().equalsIgnoreCase(productId))
                .findFirst();

        itemOpt.ifPresentOrElse(
                item -> {
                    item.setQuantity(request.quantity());
                },
                () -> {
                    throw new RuntimeException("No item found");
                }
        );

        cart.setTotal(calculateTotal(cart));

        return CartMapper.toResponse(repository.save(cart));
    }

    private double calculateTotal(Cart cart){
        return cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }
}
