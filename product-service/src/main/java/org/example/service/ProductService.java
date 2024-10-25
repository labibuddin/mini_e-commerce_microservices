package org.example.service;

import org.example.entity.Product;
import org.example.exception.ProductOutOfStockException;
import org.example.model.CreateProductRequest;
import org.example.model.ProductResponse;
import org.example.model.UpdateProductQuantity;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse create(CreateProductRequest request) {

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productRepository.save(product);

        return toProductResponse(product);

    }

    @Transactional(readOnly = true)
    public ProductResponse get(String productId) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::toProductResponse).toList();

    }

    @Transactional
    public void reduceStock(String productId, UpdateProductQuantity quantity) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStock() >= quantity.getStock()) {
            product.setStock(product.getStock()-quantity.getStock());
            productRepository.save(product);
        } else {
            throw new ProductOutOfStockException("Stock barang habis");
        }
    }

    @Transactional
    public void addStock(String productId, UpdateProductQuantity quantity) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setStock(product.getStock()+quantity.getStock());
        productRepository.save(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

}
