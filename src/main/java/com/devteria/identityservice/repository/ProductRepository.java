package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.CategoriesEntity;
import com.devteria.identityservice.entity.ProductsEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long>, JpaSpecificationExecutor<ProductsEntity> {
    List<ProductsEntity> findAll(Specification<ProductsEntity> spec);


    List<ProductsEntity> findByCategoryId(Long categoryId);
}
