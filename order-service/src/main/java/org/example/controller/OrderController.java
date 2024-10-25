package org.example.controller;

import org.example.exception.ProductOutOfStockException;
import org.example.model.CreateOrderRequest;
import org.example.model.OrderResponse;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(
            path = "api/order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            OrderResponse orderResponse = orderService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
        }catch (ProductOutOfStockException e) {
            // Jika produk habis
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Produk sudah habis ndul"));
        } catch (Exception e) {
            // Penanganan exception umum lainnya
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Terjadi kesalahan saat memproses pesanan"));
        }
    }

    @GetMapping(
            path = "api/order"
    )
    public List<OrderResponse> getAllOrders() {
        return orderService.getAll();
    }
}
