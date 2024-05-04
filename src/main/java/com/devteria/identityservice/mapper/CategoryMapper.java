package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.CategoryCreationRequest;
import com.devteria.identityservice.dto.request.CategoryUpdateRequest;
import com.devteria.identityservice.dto.response.CategoryResponse;
import com.devteria.identityservice.entity.CategoriesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoriesEntity toCategoriesEntity(CategoryCreationRequest request);
    CategoryResponse toCategoryResponse(CategoriesEntity entity);

    void updateCategoriesEntity(@MappingTarget CategoriesEntity category, CategoryUpdateRequest request);
}
