package org.user.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.cartservice.entity.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByCartId(String cartId);

    List<CartItem> findByProductId(String productId);
}
