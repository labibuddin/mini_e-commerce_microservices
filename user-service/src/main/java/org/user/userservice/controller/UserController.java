package org.user.userservice.controller;

import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.user.userservice.entity.User;
import org.user.userservice.model.*;
import org.user.userservice.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "api/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(path = "api/user/product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> createProduct(User user, @RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = userService.createProduct(user, createProductRequest);
        return WebResponse.<ProductResponse>builder().data(productResponse).message("Product berhasil dibuat").build();
    }

    @PostMapping(path = "api/user/order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<OrderResponse> createOrder(User user, @RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse orderResponse = userService.createOrder(user, createOrderRequest);
        return WebResponse.<OrderResponse>builder().data(orderResponse).message("Order berhasil dibuat").build();

    }

    @PostMapping(path = "api/user/add-product-quantity",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> addStock(User user, @RequestBody UpdateProductQuantity updateProductQuantity) {
        ProductResponse productResponse = userService.addStock(user, updateProductQuantity);
        return WebResponse.<ProductResponse>builder().data(productResponse).message(productResponse.getName() + " berhasil ditambah").build();
    }


}
