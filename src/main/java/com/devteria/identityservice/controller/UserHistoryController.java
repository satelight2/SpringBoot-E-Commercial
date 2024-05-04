package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.response.OrderHistoryResponse;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.entity.OrdersEntity;
import com.devteria.identityservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/history")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserHistoryController {
    OrderService orderService;

    @GetMapping
    ApiResponse<List<OrderHistoryResponse>> getHistoryOrders(){
        return ApiResponse.<List<OrderHistoryResponse>>builder()
                .message("Orders")
                .result(orderService.getHistoryOrders())
                .build();
    }

    //get List<OrderHistoryResponse> by serialNumber or status
    @GetMapping("/searchByOrderStatus")
    ApiResponse<?> getHistoryOrdersByKey(@RequestParam(value = "key",required = false) String key){
        return ApiResponse.<List<OrderHistoryResponse>>builder()
                .message("Orders")
                .result(orderService.getHistoryOrdersByKey(key))
                .build();
    }

    @GetMapping("/searchBySerialNumber")
    ApiResponse<?> getHistoryOrdersBySerialNumber(@RequestParam(value = "serialNumber",required = false) String serialNumber){
        return ApiResponse.<List<OrdersEntity>>builder()
                .message("Orders")
                .result(orderService.getHistoryOrdersBySerialNumber(serialNumber))
                .build();
    }

    ///api.myservice.com/v1/user/history/{orderId}/cancel	PUT	Hủy đơn hàng đang trong trạng thái chờ xác nhận
    @PutMapping("/{orderId}/cancel")
    ApiResponse<?> cancelOrder(@PathVariable Long orderId){
        String mess = orderService.cancelOrder(orderId);
        return ApiResponse.builder()
                .message(mess)
                .build();
    }

}
