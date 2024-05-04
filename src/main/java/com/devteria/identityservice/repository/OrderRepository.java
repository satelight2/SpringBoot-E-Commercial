package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long>, JpaSpecificationExecutor<OrdersEntity> {
    List<OrdersEntity> findByUserId(Long userId);
    List<OrdersEntity> findByStatus(String status);


}
