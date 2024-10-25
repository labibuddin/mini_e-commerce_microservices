package org.user.cartservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    private String id;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    private int quantity;

    private double price;
}

