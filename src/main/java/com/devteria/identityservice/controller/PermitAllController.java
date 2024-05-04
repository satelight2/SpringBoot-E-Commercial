package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.ProductKeyCreationRequest;
import com.devteria.identityservice.entity.CategoriesEntity;
import com.devteria.identityservice.entity.ProductsEntity;
import com.devteria.identityservice.repository.ProductRepository;
import com.devteria.identityservice.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermitAllController {
    ProductService productService;
    ProductRepository productRepository;


    @GetMapping("/products/{offset}/{limit}")
    Page<ProductsEntity> getProducts(@PathVariable int offset, @PathVariable int limit){
        return productService.getProducts(offset, limit);
    }
    @PostMapping("/products")
    public ApiResponse<List<ProductsEntity>> searchProducts(@RequestBody ProductKeyCreationRequest request) {
        Specification<ProductsEntity> spec = ProductService.ProductsEntitySpecifications.nameOrDescriptionContains(request);
        return ApiResponse.<List<ProductsEntity>>builder()
                .message("Products Retrieved")
                .result(productRepository.findAll(spec))
                .build();
    }

    @GetMapping("/categories")
    ApiResponse<List<CategoriesEntity>> getCategories(){
        return ApiResponse.<List<CategoriesEntity>>builder()
                .message("Categories Retrieved")
                .result(productService.getCategories())
                .build();
    }

    @PostMapping("/products/categories")
    ApiResponse<List<ProductsEntity>> getProductsByCategory(@RequestBody ProductKeyCreationRequest request){

        return ApiResponse.<List<ProductsEntity>>builder()
                .message("Products Retrieved")
                .result(productService.getProductsByCategory(request))
                .build();



    }
    ///api.myservice.com/v1/products/{productId}	GET	Chi tiết thông tin sản phẩm theo id
    @GetMapping("/products/{productId}")
    ApiResponse<ProductsEntity> getProductById(@PathVariable Long productId){
        return ApiResponse.<ProductsEntity>builder()
                .message("Product Retrieved")
                .result(productService.getProductById(productId))
                .build();
    }

}
