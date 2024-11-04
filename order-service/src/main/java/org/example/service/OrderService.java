package org.example.service;

import org.example.entity.Order;
import org.example.event.ReduceStockEvent;
import org.example.exception.ProductOutOfStockException;
import org.example.model.CreateOrderRequest;
import org.example.model.OrderResponse;
import org.example.model.ProductResponse;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${apps.product.api}")
    private String PRODUCT_SERVICE_API;

    @Autowired
    private KafkaTemplate<String, ReduceStockEvent> kafkaTemplate;

    public OrderResponse create(CreateOrderRequest request) {

        String productServiceURL = PRODUCT_SERVICE_API + request.getProductId();

        ProductResponse productResponse = restTemplate.getForObject(productServiceURL, ProductResponse.class);

        if (productResponse != null && productResponse.getStock() >= request.getQuantity()) {
            // kirim event ke Kafka
            ReduceStockEvent event = new ReduceStockEvent();
            event.setProductId(request.getProductId());
            event.setQuantity(request.getQuantity());
            kafkaTemplate.send("reduce-stock-event", event);

//            restTemplate.put(productServiceURL + "/reduce-stock", request.getQuantity());
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setProductId(request.getProductId());
            order.setProductName(productResponse.getName());
            order.setQuantity(request.getQuantity());
            orderRepository.save(order);

            return toOrderResponse(order);
        } else {
            throw new ProductOutOfStockException("Product e wes entek");
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::toOrderResponse).toList();
    }

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .build();
    }
}
