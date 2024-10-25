package org.example.controller;

import org.example.model.CreateProductRequest;
import org.example.model.ProductResponse;
import org.example.model.UpdateProductQuantity;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "api/product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productService.create(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping(path = "api/product")
    public List<ProductResponse> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping(
            path = "api/product/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable("productId") String productId
    ) {
        ProductResponse productResponse = productService.get(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping(
            path = "api/product/{productId}/reduce-stock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> reduceStock(@PathVariable("productId") String productId,
                                                         @RequestBody UpdateProductQuantity requestQuantity) {
        productService.reduceStock(productId, requestQuantity);
        return ResponseEntity.ok("stock berhasil di kurangi");
    }

    @PutMapping(
            path = "api/product/{productId}/add-stock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> addStock(@PathVariable("productId") String productId,
                                                        @RequestBody UpdateProductQuantity requestQuantity) {
        productService.addStock(productId, requestQuantity);
        return ResponseEntity.ok("stock berhasil di tambah");
    }
}
