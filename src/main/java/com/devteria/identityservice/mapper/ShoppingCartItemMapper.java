package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.ShoppingCartItemCreationRequest;
import com.devteria.identityservice.dto.request.ShoppingCartItemUpdateRequest;
import com.devteria.identityservice.dto.response.ShoppingCartItemResponse;
import com.devteria.identityservice.dto.response.ShoppingCartResponse;
import com.devteria.identityservice.entity.ShoppingCartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemMapper {
    ShoppingCartItemEntity toShoppingCartItemEntity(ShoppingCartItemCreationRequest request);

    ShoppingCartItemResponse toShoppingCartResponse(ShoppingCartItemEntity entity);
    void updateShoppingCartItemEntity(@MappingTarget ShoppingCartItemEntity entity, ShoppingCartItemUpdateRequest request);
}
