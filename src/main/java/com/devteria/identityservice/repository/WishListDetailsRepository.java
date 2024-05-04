package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.WishListDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListDetailsRepository extends JpaRepository<WishListDetailsEntity, Long> {
    WishListDetailsEntity findByWishListIdAndProductId(Long wishListId, Long productId);
}
