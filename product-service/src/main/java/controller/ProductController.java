package controller;

import dto.ProductRequest;
import dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody @Validated ProductRequest request){
        return ResponseEntity.ok(service.saveProduct(request));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getProduct(id));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(service.getAllProducts(pageable));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Validated ProductRequest request, @PathVariable @Validated Long id){
        return ResponseEntity.ok(service.updateProduct(request, id));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable @Validated String id){
        return ResponseEntity.ok(service.deleteProduct(id) + ": Product deleted successfully");
    }
}
