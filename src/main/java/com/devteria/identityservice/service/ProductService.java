package com.devteria.identityservice.service;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.devteria.identityservice.dto.request.ProductKeyCreationRequest;
import com.devteria.identityservice.dto.request.ProductRequest;
import com.devteria.identityservice.dto.request.ProductUpdateRequest;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.entity.CategoriesEntity;
import com.devteria.identityservice.entity.ProductsEntity;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.mapper.ProductMapper;
import com.devteria.identityservice.repository.CategoryRepository;
import com.devteria.identityservice.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    public List<ProductResponse> getAll(){
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse create(ProductRequest request){
        ProductsEntity product = productMapper.toProductsEntity(request);

        product.setSku(UUID.randomUUID().toString());
        product.setStockQuantity(10);

        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse update(Long productId, ProductUpdateRequest request) {
        ProductsEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProductsEntity(product, request);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse delete(Long productId) {
        ProductsEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse getOne(Long productId) {
        ProductsEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    public Page<ProductsEntity> getProducts(int page, int size) {
//        return productRepository.findAll(PageRequest.of(page, size));
        Page<ProductsEntity> products = productRepository.findAll(PageRequest.of(page, size));
        return products;
    }

    public List<CategoriesEntity> getCategories() {
        return categoryRepository.findAll();
    }

    public List<ProductsEntity> getProductsByCategory(ProductKeyCreationRequest request) {
        String categoryE = request.getKeyword();
        // if category name is long
        if (categoryE.matches("\\d+")) {
            Long categoryId = Long.parseLong(categoryE);
            return productRepository.findByCategoryId(categoryId);
        }else{
            CategoriesEntity category = categoryRepository.findByCategoryName(categoryE);
            if (category == null) {
                throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
            }
            return productRepository.findByCategoryId(category.getCategoryId());
        }
    }

    public ProductsEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
    }


    public class ProductsEntitySpecifications {
        public static Specification<ProductsEntity> nameOrDescriptionContains(ProductKeyCreationRequest keyword) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(root.get("productName"), "%" + keyword.getKeyword() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + keyword.getKeyword()+ "%")
                    );
        }
    }




    public class ProSpecifications {
        public static Specification<ProductsEntity> byCategoryId(Long categoryId) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        }

        public static Specification<ProductsEntity> byCategoryName(String categoryName) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("name"), categoryName);
        }
    }

}
