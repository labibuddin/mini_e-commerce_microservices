package org.user.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.cartservice.entity.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByUserId(String userId);

    List<Cart> findByStatus(String status);
}
