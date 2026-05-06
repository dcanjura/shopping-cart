package com.cart.service.service;

import com.cart.service.client.OrderClient;
import com.cart.service.client.ProductClient;
import com.cart.service.domain.Cart;
import com.cart.service.domain.Item;
import com.cart.service.dto.*;
import com.cart.service.exception.BadRequestException;
import com.cart.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.cart.service.mapper.CartMapper;
import org.springframework.stereotype.Service;
import com.cart.service.repository.CartRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final ProductClient productClient;
    private final OrderClient orderClient;

    /**
     * Creates a cart for a user to add items
     * @param request
     * @return CartResponse when object is created
     */
    public CartResponse create(CreateCartRequest request){
        if(request.userId() == null || request.userId().isBlank()){
            throw new BadRequestException("UserId is required");
        }

        repository.findByUserId(request.userId())
                .stream().findFirst().ifPresent(c -> {
                    throw new BadRequestException("Cart already exists for this user");
                });

        return CartMapper.toResponse(repository.save(Cart.builder()
                .userId(request.userId())
                .items(new ArrayList<>())
                .total(0.0)
                .build()));
    }

    /**
     * Gets cart information when found
     * @param cartId
     * @return CartResponse
     */
    public CartResponse getCartById(Long cartId){
        if(isNullLessThan(cartId)){
            throw new BadRequestException("cartId not found or not provided");
        }

        return CartMapper.toResponse(repository.getCartById(cartId));
    }

    /**
     * Adds item information received to a cart
     * @param request
     * @param cartId
     * @return CartResponse with information updated
     */
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

    /**
     * Removes an item based on the information received
     * @param cartId
     * @param productId
     * @return CartResponse with information updated
     */
    public CartResponse removeItem(Long cartId, String productId){
        if(isNullLessThan(cartId)){
            throw new BadRequestException("cartId not found or not provided");
        }

        Cart cart = repository.getCartById(cartId);

        cart.getItems().removeIf(item -> (item.getProductId().equalsIgnoreCase(productId)));

        cart.setTotal(calculateTotal(cart));

        return CartMapper.toResponse(repository.save(cart));
    }

    /**
     * Updates an item within cart information
     * @param request
     * @param cartId
     * @param productId
     * @return CartResponse with information updated
     */
    public CartResponse updateItem(UpdateItemRequest request, Long cartId, String productId){
        if (isNullLessThan(cartId)) {
            throw new BadRequestException("cartId not found or not provided");
        }

        Cart cart = getCartInfo(cartId);

        Optional<Item> itemOpt = Optional.of(cart.getItems().stream()
                .filter(i -> i.getProductId().equalsIgnoreCase(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No product info found with productId: " + productId)));

        itemOpt.ifPresentOrElse(
                item -> {
                    item.setQuantity(request.quantity());
                },
                () -> {
                    throw new NotFoundException("No information found for productId: " + productId);
                }
        );

        cart.setTotal(calculateTotal(cart));

        return CartMapper.toResponse(repository.save(cart));
    }

    /**
     * Calls OrderService in order to go through the checkout process
     * @param cartId
     * @return OrderResponse
     */
    public OrderResponse checkout(Long cartId){
        if (isNullLessThan(cartId)) {
            throw new BadRequestException("cartId not found or not provided");
        }

        CartResponse cart = CartMapper.toResponse(getCartInfo(cartId));

        OrderRequest request = new OrderRequest(
                String.valueOf(cartId),
                cart.userId(),
                PaymentType.CARD
        );

        return orderClient.createOrder(request);
    }

    private double calculateTotal(Cart cart){
        return cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    private Cart getCartInfo(Long id){
        Cart cart = repository.getCartById(id);
        if(cart != null){
            return cart;
        }else {
            throw new NotFoundException("No cart info found with id: " + id);
        }
    }

    private boolean isNullLessThan(Long value){
        return (value == null || value < 1);
    }
}
