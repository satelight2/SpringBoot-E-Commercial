package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.ShoppingCartItemCreationRequest;
import com.devteria.identityservice.dto.request.ShoppingCartItemUpdateRequest;
import com.devteria.identityservice.dto.response.ShoppingCartItemResponse;
import com.devteria.identityservice.dto.response.ShoppingCartResponse;
import com.devteria.identityservice.entity.ShoppingCartEntity;
import com.devteria.identityservice.entity.ShoppingCartItemEntity;
import com.devteria.identityservice.entity.UsersEntity;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.mapper.ShoppingCartItemMapper;
import com.devteria.identityservice.repository.ShoppingCartItemRepository;
import com.devteria.identityservice.repository.ShoppingCartRepository;
import com.devteria.identityservice.repository.UserRepository;
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
public class ShoppingCartService {
    ShoppingCartItemRepository shoppingCartItemRepository;
    ShoppingCartRepository shoppingCartRepository;
    UserRepository userRepository;
    ShoppingCartItemMapper shoppingCartItemMapper;


    public ShoppingCartResponse createShoppingCart() {
        log.info("Service: Create Shopping Cart");
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Long userId = user.getUserId();
        shoppingCart.setUserId(userId);
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartResponse.builder()
                .shoppingCartId(shoppingCart.getShoppingCartId())
                .userId(userId)
                .build();
    }

    public ShoppingCartItemResponse addItemToShoppingCart(long shoppingCartID, ShoppingCartItemCreationRequest shoppingCartItemCreationRequest) {
        log.info("Service: Add Item to Shopping Cart");
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartID).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }
        ShoppingCartItemEntity shoppingCartItem = shoppingCartItemMapper.toShoppingCartItemEntity(shoppingCartItemCreationRequest);
        shoppingCartItem.setShoppingCartId(shoppingCartID);

        return shoppingCartItemMapper.toShoppingCartResponse(shoppingCartItemRepository.save(shoppingCartItem));


    }

    public List<ShoppingCartItemResponse> getShoppingCartItems(long shoppingCartID) {
        log.info("Service: Get Shopping Cart Items");
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartID).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }

        return shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartID)
                .stream()
                .map(shoppingCartItemMapper::toShoppingCartResponse)
                .toList();

    }

    public ShoppingCartItemResponse updateShoppingCartItem(long shoppingCartID, long shoppingCartItemId, ShoppingCartItemUpdateRequest request) {
        log.info("Service: Update Shopping Cart Item");
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartID).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }

        ShoppingCartItemEntity shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_ITEM_NOT_EXISTED));

        shoppingCartItemMapper.updateShoppingCartItemEntity(shoppingCartItem, request);
        return shoppingCartItemMapper.toShoppingCartResponse(shoppingCartItemRepository.save(shoppingCartItem));
    }

    public ShoppingCartItemResponse deleteShoppingCartItem(long shoppingCartID, long shoppingCartItemId) {
        log.info("Service: Delete Shopping Cart Item");
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartID).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }

        ShoppingCartItemEntity shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_ITEM_NOT_EXISTED));

        shoppingCartItemRepository.delete(shoppingCartItem);
        return shoppingCartItemMapper.toShoppingCartResponse(shoppingCartItem);
    }

    public List<ShoppingCartItemResponse> deleteAllShoppingCartItems(long shoppingCartID) {
        log.info("Service: Delete All Shopping Cart Items");
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartID).orElseThrow(
                () -> new AppException(ErrorCode.SHOPPING_CART_NOT_EXISTED));

        //check if the user is the owner of the shopping cart
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!shoppingCart.getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_OWNER_OF_SHOPPING_CART);
        }

        List<ShoppingCartItemEntity> shoppingCartItems = shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartID);
        shoppingCartItemRepository.deleteAll(shoppingCartItems);
        return shoppingCartItems.stream()
                .map(shoppingCartItemMapper::toShoppingCartResponse)
                .toList();
    }

    //check if the user has a shopping cart and show it


}
