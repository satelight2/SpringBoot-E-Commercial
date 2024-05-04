package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "shopping_cart_item", schema = "ecommerce_web")
public class ShoppingCartItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shopping_cart_item_id")
     long shoppingCartItemId;
    @Basic
    @Column(name = "shopping_cart_id")
     Long shoppingCartId;
    @Basic
    @Column(name = "product_id")
     Long productId;
    @Basic
    @Column(name = "order_quantity")
     Integer orderQuantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItemEntity that = (ShoppingCartItemEntity) o;
        return shoppingCartItemId == that.shoppingCartItemId && Objects.equals(shoppingCartId, that.shoppingCartId) && Objects.equals(productId, that.productId) && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingCartItemId, shoppingCartId, productId, orderQuantity);
    }
}
