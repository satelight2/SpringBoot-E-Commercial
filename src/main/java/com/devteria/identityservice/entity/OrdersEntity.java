package com.devteria.identityservice.entity;

import com.devteria.identityservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders", schema = "ecommerce_web")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
     long orderId;
    @Basic
    @Column(name = "serial_number")
     String serialNumber;
    @Basic
    @Column(name = "user_id")
     long userId;
    @Basic
    @Column(name = "total_price")
     BigDecimal totalPrice;
    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderStatus status;
    @Basic
    @Column(name = "note")
     String note;
    @Basic
    @Column(name = "receive_name")
     String receiveName;
    @Basic
    @Column(name = "receive_address")
     String receiveAddress;
    @Basic
    @Column(name = "receive_phone")
     String receivePhone;
    @Basic
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date createdAt;
    @Basic
    @Column(name = "received_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
     Date receivedAt;

    @JsonManagedReference
    @OneToMany(mappedBy ="orderId", fetch = FetchType.LAZY)
    Set<OrderDetailsEntity> listItem;





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId && userId == that.userId && Objects.equals(serialNumber, that.serialNumber) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(status, that.status) && Objects.equals(note, that.note) && Objects.equals(receiveName, that.receiveName) && Objects.equals(receiveAddress, that.receiveAddress) && Objects.equals(receivePhone, that.receivePhone) && Objects.equals(createdAt, that.createdAt) && Objects.equals(receivedAt, that.receivedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, serialNumber, userId, totalPrice, status, note, receiveName, receiveAddress, receivePhone, createdAt, receivedAt);
    }
}
