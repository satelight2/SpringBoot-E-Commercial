package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.WishListDetailsCreationRequest;
import com.devteria.identityservice.entity.WishListEntity;
import com.devteria.identityservice.service.WishListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/wishlist")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserWishListController {
    WishListService wishListService;

    @GetMapping()
    ApiResponse<List<WishListEntity>> getWishList(){
        return ApiResponse.<List<WishListEntity>>builder()
                .message("Wish List Retrieved")
                .result(wishListService.getWishList())
                .build();
    }
    @PostMapping()
    ApiResponse<WishListEntity> createWishList(){
        return ApiResponse.<WishListEntity>builder()
                .message("Wish List Created")
                .result(wishListService.createWishList())
                .build();
    }

    @PostMapping("/{wishListId}/add")
    ApiResponse<WishListEntity> addItemToWishList(
            @PathVariable Long wishListId,
            @RequestBody WishListDetailsCreationRequest request){
        return ApiResponse.<WishListEntity>builder()
                .message("Item added to Wish List")
                .result(wishListService.addItemToWishList(wishListId, request))
                .build();
    }

    @DeleteMapping("/{wishListId}/deleteItem")
    ApiResponse<WishListEntity> deleteItemWishList(
            @PathVariable Long wishListId,
            @RequestBody WishListDetailsCreationRequest request){
        return ApiResponse.<WishListEntity>builder()
                .message("Item deleted from Wish List")
                .result(wishListService.deleteItemWishList(wishListId, request))
                .build();
    }
}
