package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.*;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {
    ProductService productService;

    @GetMapping
     ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAll())
                .build();
    }

    @PostMapping
     ApiResponse<ProductResponse> create(@RequestBody @Valid ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.create(request))
                .build();
    }
    @PutMapping("/{productId}")
     ApiResponse<ProductResponse> update(@PathVariable Long productId, @RequestBody ProductUpdateRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.update(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}")
     ApiResponse<ProductResponse> delete(@PathVariable Long productId){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.delete(productId))
                .build();
    }

    @GetMapping("/{productId}")
     ApiResponse<ProductResponse> getOne(@PathVariable Long productId){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getOne(productId))
                .build();
    }

}
