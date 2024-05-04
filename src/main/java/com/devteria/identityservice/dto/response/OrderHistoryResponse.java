package com.devteria.identityservice.dto.response;

import com.devteria.identityservice.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderHistoryResponse {
    String serialNumber;

    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    String note;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date createdAt;


}
