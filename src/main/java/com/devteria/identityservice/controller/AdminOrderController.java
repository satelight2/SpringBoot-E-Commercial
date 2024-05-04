package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.OrderUpdateStatusRequest;
import com.devteria.identityservice.entity.OrdersEntity;
import com.devteria.identityservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminOrderController {
    OrderService orderService;

    @GetMapping("")
    ApiResponse<List<OrdersEntity>> getOrders(){
        return ApiResponse.<List<OrdersEntity>>builder()
                .message("Orders")
                .result(orderService.getOrders())
                .build();
    }

    @GetMapping("/status")
    ApiResponse<List<OrdersEntity>> getOrdersByStatus(@RequestParam(value = "key",required = false) String key){
        return ApiResponse.<List<OrdersEntity>>builder()
                .message("Orders")
                .result(orderService.getOrdersByStatus(key))
                .build();
    }

    @GetMapping("/{orderID}")
    ApiResponse<OrdersEntity> getOrderById(@PathVariable  Long orderID){
        return ApiResponse.<OrdersEntity>builder()
                .message("Order")
                .result(orderService.getOrderById(orderID))
                .build();
    }

    @PutMapping("/{orderID}/status")
    ApiResponse<OrdersEntity> updateOrderStatus(@PathVariable Long orderID, @RequestBody OrderUpdateStatusRequest status){
        return ApiResponse.<OrdersEntity>builder()
                .message("Order status has been updated")
                .result(orderService.updateOrderStatus(orderID, status))
                .build();
    }

}
