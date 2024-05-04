package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.ProductRequest;
import com.devteria.identityservice.dto.request.ProductUpdateRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.entity.ProductsEntity;
import com.devteria.identityservice.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductsEntity toProductsEntity(ProductRequest request);
    ProductResponse toProductResponse(ProductsEntity entity);

    void updateProductsEntity(@MappingTarget ProductsEntity entity, ProductUpdateRequest request);

}
