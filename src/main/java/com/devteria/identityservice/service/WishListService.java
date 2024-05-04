package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.WishListDetailsCreationRequest;
import com.devteria.identityservice.entity.ProductsEntity;
import com.devteria.identityservice.entity.UsersEntity;
import com.devteria.identityservice.entity.WishListDetailsEntity;
import com.devteria.identityservice.entity.WishListEntity;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.ProductRepository;
import com.devteria.identityservice.repository.UserRepository;
import com.devteria.identityservice.repository.WishListDetailsRepository;
import com.devteria.identityservice.repository.WishListRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WishListService {
    WishListRepository wishListRepository;
    WishListDetailsRepository wishListDetailsRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public WishListEntity createWishList() {
        log.info("Service: Create Wish List");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Long userId = user.getUserId();
        WishListEntity wishList = new WishListEntity();
        wishList.setUserId(userId);
        wishListRepository.save(wishList);
        return wishList;
    }

    public WishListEntity addItemToWishList(Long wishListId, WishListDetailsCreationRequest request) {
        log.info("Service: Add Item to Wish List");
        WishListEntity wishList = wishListRepository.findById(wishListId).orElseThrow(
                () -> new AppException(ErrorCode.WISH_LIST_NOT_EXISTED));
        Long productId = request.getProductId();
        ProductsEntity product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        WishListDetailsEntity wishListDetails = new WishListDetailsEntity();
        wishListDetails.setWishListId(wishListId);
        wishListDetails.setProductId(productId);
        wishListDetails.setProductName(product.getProductName());
        wishListDetails.setImage(product.getImage());

        wishListDetailsRepository.save(wishListDetails);
        return wishList;
    }



    public WishListEntity deleteItemWishList(Long wishListId, WishListDetailsCreationRequest request) {
        log.info("Service: Delete Item from Wish List");
        WishListEntity wishList = wishListRepository.findById(wishListId).orElseThrow(
                () -> new AppException(ErrorCode.WISH_LIST_NOT_EXISTED));
        Long productId = request.getProductId();
        WishListDetailsEntity wishListDetails = wishListDetailsRepository.findByWishListIdAndProductId(wishListId, productId);

        if(wishListDetails == null) {
            throw new AppException(ErrorCode.WISH_LIST_DETAILS_NOT_EXISTED);
        }

        wishListDetailsRepository.delete(wishListDetails);
        return wishList;
    }

    public List<WishListEntity> getWishList() {
        log.info("Service: Get Wish List");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Long userId = user.getUserId();
        return wishListRepository.findByUserId(userId);
    }
}
