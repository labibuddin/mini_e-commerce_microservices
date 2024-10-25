package org.user.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user.cartservice.model.AddItemToCartRequest;
import org.user.cartservice.model.CartResponse;
import org.user.cartservice.model.CreateCartRequest;
import org.user.cartservice.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CreateCartRequest request) {
        CartResponse response = cartService.createCart(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items/{cart-id}")
    public ResponseEntity<String> addItem(@PathVariable("cart-id") String cartId, @RequestBody AddItemToCartRequest request) {
        cartService.addItemToCart(cartId ,request);
        return ResponseEntity.ok().build();
    }
}
