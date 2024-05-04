package com.devteria.identityservice.model;

import com.devteria.identityservice.entity.OrdersEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification implements Specification<OrdersEntity>{
    private String value;
    private String fieldName;
    private String operation;
    @Override
    public Predicate toPredicate(Root<OrdersEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(root.get("orderId"), value));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
