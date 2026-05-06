package com.cart.service.controller;

import com.cart.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cart.service.service.CartService;

@RestController
@RequestMapping("/cart")
@Validated
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @GetMapping("/internal/{cartId}")
    public ResponseEntity<CartResponse> getCartInternal(@PathVariable Long cartId){
        return ResponseEntity.ok(service.getCartById(cartId));
    }

    /**
     * Create the shopping cart using userId from request body
     * @param request
     * @return CartResponse
     */
    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCart(@RequestBody @Validated CreateCartRequest request){
        return ResponseEntity.status(201).body(service.create(request));
    }

    /**
     * Get the cart info using userId from request
     * @param cartId
     * @return CartResponse
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId){
        return ResponseEntity.ok(service.getCartById(cartId));
    }

    @PostMapping("/addItem/{cartId}")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Validated AddItemRequest request, @PathVariable Long cartId){
        return ResponseEntity.ok(service.addItem(request, cartId));
    }

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<OrderResponse> checkout(@PathVariable Long cartId){
        return ResponseEntity.ok(service.checkout(cartId));
    }

    @PutMapping("/updateItem/{cartId}/{productId}")
    public ResponseEntity<CartResponse> updateItem(@RequestBody @Validated UpdateItemRequest request, @PathVariable @Validated Long cartId, @PathVariable @Validated String productId){
        return ResponseEntity.ok(service.updateItem(request, cartId, productId));
    }

    @DeleteMapping("/removeItem/{cartId}/{productId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable @Validated Long cartId, @PathVariable @Validated String productId){
        return ResponseEntity.status(204).body(service.removeItem(cartId, productId));
    }
}
