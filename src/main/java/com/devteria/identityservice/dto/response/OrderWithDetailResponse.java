package com.devteria.identityservice.dto.response;

import com.devteria.identityservice.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderWithDetailResponse {

    String serialNumber;

    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    String note;

    String receiveName;

    String receiveAddress;

    String receivePhone;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date createdAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date receivedAt;

    Set<OrderDetailResponse> orderDetails;

}
