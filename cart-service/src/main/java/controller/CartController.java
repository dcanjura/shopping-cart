package controller;

import dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.CartService;

@RestController
@RequestMapping("/cart")
@Validated
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    /**
     * Create the shopping cart using userId from request body
     * @param request
     * @return CartResponse
     */
    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCart(@RequestBody @Validated CreateCartRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    /**
     * Get the cart info using userId from request
     * @param request
     * @return CartResponse
     */
    @GetMapping("/getCartByUser")
    public ResponseEntity<CartResponse> getByUserId(@RequestBody @Validated CreateCartRequest request){
        return ResponseEntity.ok(service.getByUser(request));
    }

    @PostMapping("/addItem/{cartId}")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Validated AddItemRequest request, @PathVariable Long cartId){
        return ResponseEntity.ok(service.addItem(request, cartId));
    }

    @PutMapping("/updateItem/{cartId}/{productId}")
    public ResponseEntity<CartResponse> updateItem(@RequestBody @Validated UpdateItemRequest request, @PathVariable @Validated Long cartId, @PathVariable @Validated String productId){
        return ResponseEntity.ok(service.updateItem(request, cartId, productId));
    }

    @DeleteMapping("/removeItem/{cartId}/{productId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable @Validated Long cartId, @PathVariable @Validated String productId){
        return ResponseEntity.ok(service.removeItem(cartId, productId));
    }
}
