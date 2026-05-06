package com.order.service.controller;

import com.order.service.dto.OrderRequest;
import com.order.service.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.order.service.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping("/save")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Validated OrderRequest request){
        return ResponseEntity.ok(service.createOrder(request));
    }
}
