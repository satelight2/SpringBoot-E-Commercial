package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products", schema = "ecommerce_web")
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
     long productId;
    @Basic
    @Column(name = "sku")
     String sku;
    @Basic
    @Column(name = "product_name")
     String productName;
    @Basic
    @Column(name = "description")
     String description;
    @Basic
    @Column(name = "unit_price")
     BigDecimal unitPrice;
    @Basic
    @Column(name = "stock_quantity")
     Integer stockQuantity;
    @Basic
    @Column(name = "image")
     String image;
    @Basic
    @Column(name = "category_id")
     Long categoryId;
    @Basic
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date createdAt;
    @Basic
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
     Date updatedAt;


//    @ManyToOne
//    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
//    CategoriesEntity categoriesEntity;
//
//    @OneToOne(mappedBy = "productId")
//    OrderDetailsEntity orderDetailsEntity;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && Objects.equals(sku, that.sku) && Objects.equals(productName, that.productName) && Objects.equals(description, that.description) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(stockQuantity, that.stockQuantity) && Objects.equals(image, that.image) && Objects.equals(categoryId, that.categoryId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sku, productName, description, unitPrice, stockQuantity, image, categoryId, createdAt, updatedAt);
    }
}
