package com.devteria.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "wish_list", schema = "ecommerce_web")
public class WishListEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wish_list_id")
    private long wishListId;
    @Basic
    @Column(name = "user_id")
    private Long userId;

    @JsonManagedReference
    @OneToMany(mappedBy ="wishListId", fetch = FetchType.LAZY)
    Set<WishListDetailsEntity> listItemWishList;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishListEntity that = (WishListEntity) o;
        return wishListId == that.wishListId && Objects.equals(userId, that.userId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListId, userId);
    }
}
