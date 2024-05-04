package com.devteria.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "wish_list_detais", schema = "ecommerce_web")
public class WishListDetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wish_list_detail_id")
    private long wishListDetailId;
    @Basic
    @Column(name = "wish_list_id")
    private Long wishListId;
    @Basic
    @Column(name = "product_id")
    private Long productId;
    @Basic
    @Column(name = "product_name")
    String productName;
    @Basic
    @Column(name = "image")
    String image;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_list_id", referencedColumnName = "wish_list_id",insertable = false, updatable = false)
    WishListEntity wishListEntity;

}
