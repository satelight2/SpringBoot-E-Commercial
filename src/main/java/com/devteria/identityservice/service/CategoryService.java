package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.CategoryCreationRequest;
import com.devteria.identityservice.dto.request.CategoryUpdateRequest;
import com.devteria.identityservice.dto.response.CategoryResponse;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.entity.CategoriesEntity;
import com.devteria.identityservice.mapper.CategoryMapper;
import com.devteria.identityservice.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService{
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryCreationRequest request) {
        CategoriesEntity category = categoryMapper.toCategoriesEntity(request);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public CategoryResponse update(Long categoryId, CategoryUpdateRequest request) {
        CategoriesEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryMapper.updateCategoriesEntity(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse getOne(Long categoryId) {
        CategoriesEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse delete(Long categoryId) {
        CategoriesEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return categoryMapper.toCategoryResponse(category);
    }
}
