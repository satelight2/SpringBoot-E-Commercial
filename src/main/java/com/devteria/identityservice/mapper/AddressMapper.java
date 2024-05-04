package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.AddressCreationRequest;
import com.devteria.identityservice.dto.request.AddressUpdateRequest;
import com.devteria.identityservice.dto.request.CategoryCreationRequest;
import com.devteria.identityservice.dto.request.CategoryUpdateRequest;
import com.devteria.identityservice.dto.response.AddressResponse;
import com.devteria.identityservice.dto.response.CategoryResponse;
import com.devteria.identityservice.entity.AddressEntity;
import com.devteria.identityservice.entity.CategoriesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
//    CategoriesEntity toCategoriesEntity(CategoryCreationRequest request);
//    CategoryResponse toCategoryResponse(CategoriesEntity entity);
//
//    void updateCategoriesEntity(@MappingTarget CategoriesEntity category, CategoryUpdateRequest request);
    AddressEntity toAddressEntity(AddressCreationRequest request);
    AddressResponse toAddressResponse(AddressEntity entity);

    void updateAddressEntity(@MappingTarget AddressEntity address, AddressUpdateRequest request);
}
