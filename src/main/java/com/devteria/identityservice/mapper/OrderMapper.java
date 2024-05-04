package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.response.CategoryResponse;
import com.devteria.identityservice.dto.response.OrderHistoryResponse;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.entity.CategoriesEntity;
import com.devteria.identityservice.entity.OrdersEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    CategoryResponse toCategoryResponse(CategoriesEntity entity);

    OrderResponse toOrderResponse(OrdersEntity entity);
    OrderHistoryResponse toOrderHistoryResponse(OrdersEntity entity);
}
