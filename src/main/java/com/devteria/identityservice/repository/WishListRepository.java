package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long>, JpaSpecificationExecutor<WishListEntity> {
    List<WishListEntity> findByUserId(Long userId);
}
