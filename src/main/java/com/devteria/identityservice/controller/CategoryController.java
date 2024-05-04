package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.CategoryCreationRequest;
import com.devteria.identityservice.dto.request.CategoryUpdateRequest;
import com.devteria.identityservice.dto.request.ProductRequest;
import com.devteria.identityservice.dto.response.CategoryResponse;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("")
    ApiResponse<CategoryResponse> create(@RequestBody @Valid CategoryCreationRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<CategoryResponse>> getAll(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> update(@PathVariable Long categoryId, @RequestBody CategoryUpdateRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(categoryId, request))
                .build();
    }


    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> getOne(@PathVariable Long categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getOne(categoryId))
                .build();
    }

    @DeleteMapping("/{categoryId}")

    ApiResponse<CategoryResponse> delete(@PathVariable Long categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.delete(categoryId))
                .build();
    }
}
