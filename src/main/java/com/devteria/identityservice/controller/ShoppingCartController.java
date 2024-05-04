package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.OrderCreationRequest;
import com.devteria.identityservice.dto.request.ShoppingCartItemCreationRequest;
import com.devteria.identityservice.dto.request.ShoppingCartItemUpdateRequest;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.dto.response.ShoppingCartItemResponse;
import com.devteria.identityservice.dto.response.ShoppingCartResponse;
import com.devteria.identityservice.service.OrderService;
import com.devteria.identityservice.service.ShoppingCartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shopping-cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoppingCartController {
    ShoppingCartService shoppingCartService;
    OrderService orderService;

    @PostMapping("")
    ApiResponse<ShoppingCartResponse> createShoppingCart(){
        return ApiResponse.<ShoppingCartResponse>builder()
                .message("Shopping cart has been created")
                .result(shoppingCartService.createShoppingCart())
                .build();
    }

    @PostMapping("/{shoppingCartID}/add-item")
    ApiResponse<ShoppingCartItemResponse> addItemToShoppingCart(@PathVariable long shoppingCartID, @RequestBody ShoppingCartItemCreationRequest shoppingCartItemCreationRequest){
        return ApiResponse.<ShoppingCartItemResponse>builder()
                .message("Item has been added to the shopping cart")
                .result(shoppingCartService.addItemToShoppingCart(shoppingCartID, shoppingCartItemCreationRequest))
                .build();
    }

    @GetMapping("/{shoppingCartID}")
    ApiResponse<List<ShoppingCartItemResponse>> getShoppingCartItems(@PathVariable long shoppingCartID){
        return ApiResponse.<List<ShoppingCartItemResponse>>builder()
                .message("Shopping cart items")
                .result(shoppingCartService.getShoppingCartItems(shoppingCartID))
                .build();
    }

    @PutMapping("/{shoppingCartID}/update-item/{shoppingCartItemId}")
    ApiResponse<ShoppingCartItemResponse> updateShoppingCartItem(
            @PathVariable long shoppingCartID,
            @PathVariable long shoppingCartItemId,
            @RequestBody ShoppingCartItemUpdateRequest request){
        return ApiResponse.<ShoppingCartItemResponse>builder()
                .message("Item has been updated")
                .result(shoppingCartService.updateShoppingCartItem(shoppingCartID, shoppingCartItemId, request))
                .build();
    }

    @DeleteMapping("/{shoppingCartID}/delete-item/{shoppingCartItemId}")
    ApiResponse<ShoppingCartItemResponse> deleteShoppingCartItem(
            @PathVariable long shoppingCartID,
            @PathVariable long shoppingCartItemId){
        return ApiResponse.<ShoppingCartItemResponse>builder()
                .message("Item has been deleted")
                .result(shoppingCartService.deleteShoppingCartItem(shoppingCartID, shoppingCartItemId))
                .build();
    }

    //delete all items in the shopping cart
    @DeleteMapping("/{shoppingCartID}/delete-all-items")
    ApiResponse<List<ShoppingCartItemResponse>> deleteAllShoppingCartItems(@PathVariable long shoppingCartID){
        return ApiResponse.<List<ShoppingCartItemResponse>>builder()
                .message("All items have been deleted")
                .result(shoppingCartService.deleteAllShoppingCartItems(shoppingCartID))
                .build();
    }

    @PostMapping("/{shoppingCartID}/checkout")
    ApiResponse<OrderResponse> checkout(@PathVariable long shoppingCartID, @RequestBody OrderCreationRequest request){
        return ApiResponse.<OrderResponse>builder()
                .message("Order has been created")
                .result(orderService.getOrder(shoppingCartID, request))
                .build();
    }
}
