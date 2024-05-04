package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.RevenueRequest;
import com.devteria.identityservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/admin/dash-board")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminDashboardController {
    OrderService orderService;

    @GetMapping("/sales")
    ApiResponse<?> calculateRevenue(@Valid  @RequestBody RevenueRequest request){
        Date fromDate = request.getFromDate();
        Date toDate = request.getToDate();
        return ApiResponse.builder()
                .message("Revenue from " + fromDate + " to " + toDate + " is: ")
                .result(orderService.calculateRevenue(fromDate, toDate))
                .build();
    }
}
