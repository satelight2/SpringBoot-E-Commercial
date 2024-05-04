package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.ShoppingCartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItemEntity, Long> {
//    List<ShoppingCartItemEntity> findAllByShoppingCartId(long shoppingCartID);

    List<ShoppingCartItemEntity> findAllByShoppingCartId(long shoppingCartID);


}
