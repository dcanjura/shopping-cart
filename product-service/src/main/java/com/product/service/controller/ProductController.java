package com.product.service.controller;

import com.product.service.dto.ProductRequest;
import com.product.service.dto.ProductResponse;
import com.product.service.dto.ReduceStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.product.service.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody @Validated ProductRequest request){
        return ResponseEntity.ok(service.saveProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(service.getAllProducts(pageable));
    }

    @PostMapping("/reduce-stock")
    public void reduceStock(@RequestBody @Validated ReduceStockRequest request){
        service.reduceStock(request.productId(), request.quantity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Validated ProductRequest request, @PathVariable @Validated Long id){
        return ResponseEntity.ok(service.updateProduct(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable @Validated String id){
        return ResponseEntity.ok(service.deleteProduct(id) + ": Product deleted successfully");
    }
}
