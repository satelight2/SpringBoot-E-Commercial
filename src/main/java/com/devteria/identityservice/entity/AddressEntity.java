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
@Table(name = "address", schema = "ecommerce_web")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id")
     long addressId;

    @Basic
    @Column(name = "user_id")
     Long userId;

    @Basic
    @Column(name = "full_address")
     String fullAddress;

    @Basic
    @Column(name = "phone")
     String phone;

    @Basic
    @Column(name = "receive_name")
     String receiveName;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return addressId == that.addressId && Objects.equals(userId, that.userId) && Objects.equals(fullAddress, that.fullAddress) && Objects.equals(phone, that.phone) && Objects.equals(receiveName, that.receiveName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, fullAddress, phone, receiveName);
    }
}
