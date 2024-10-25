package org.user.cartservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.user.cartservice.entity.Cart;
import org.user.cartservice.entity.CartItem;
import org.user.cartservice.model.AddItemToCartRequest;
import org.user.cartservice.model.CartItemResponse;
import org.user.cartservice.model.CartResponse;
import org.user.cartservice.model.CreateCartRequest;
import org.user.cartservice.repository.CartItemRepository;
import org.user.cartservice.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    public CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    public CartResponse createCart(CreateCartRequest request) {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setUserId(request.getUserId());
        cart.setTotalPrice(0.0);
        cart.setStatus("active");
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        Cart savedCart = cartRepository.save(cart);

        return convertToCartResponse(savedCart, List.of());

    }

    @Transactional
    public void addItemToCart(String cartId, AddItemToCartRequest request) {

        CartItem cartItem = new CartItem();

        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        cartItem.setId(UUID.randomUUID().toString());
        cartItem.setCartId(cart.getId());
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPrice(request.getPrice());
        cartItem.setProductName(request.getProductName());

        cartItemRepository.save(cartItem);
    }

    private CartResponse convertToCartResponse(Cart cart, List<CartItem> items) {
        List<CartItemResponse> itemResponses = items.stream().map(item -> {
            CartItemResponse itemResponse = new CartItemResponse();
            itemResponse.setProductId(item.getProductId());
            itemResponse.setProductName(item.getProductName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            return itemResponse;
        }).collect(Collectors.toList());

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getId());
        cartResponse.setUserId(cart.getUserId());
        cartResponse.setItems(itemResponses);
        cartResponse.setTotalPrice(cart.getTotalPrice());
        cartResponse.setStatus(cart.getStatus());

        return cartResponse;
    }
}
