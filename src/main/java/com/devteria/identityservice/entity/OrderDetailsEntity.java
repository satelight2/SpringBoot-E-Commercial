package com.devteria.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "order_details", schema = "ecommerce_web")
//@IdClass(OrderDetailsEntityPK.class)
public class OrderDetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_details_id")
    long orderDetailsId;

    @Column(name = "order_id")
     long orderId;

    @Column(name = "product_id")
     long productId;
    @Basic
    @Column(name = "name")
     String name;
    @Basic
    @Column(name = "unit_price")
     BigDecimal unitPrice;
    @Basic
    @Column(name = "order_quantity")
     Integer orderQuantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id",insertable = false, updatable = false)
    OrdersEntity ordersEntity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
//    ProductsEntity product;


    // Getter và setter cho product

//    public String getImageUrl() {
//        if (product != null) {
//            return product.getImage();
//        }
//        return null;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return orderId == that.orderId && productId == that.productId && Objects.equals(name, that.name) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, name, unitPrice, orderQuantity);
    }
}
