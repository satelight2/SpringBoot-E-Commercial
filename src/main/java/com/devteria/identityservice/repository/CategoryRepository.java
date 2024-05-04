package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoriesEntity, Long> {
//    CategoriesEntity findByDescription(String categoryName);
    CategoriesEntity findByCategoryName(String categoryName);
}
