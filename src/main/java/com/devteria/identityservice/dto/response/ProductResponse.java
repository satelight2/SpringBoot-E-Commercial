package com.devteria.identityservice.dto.response;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    long productId;
    String productName;
    String description;
    BigDecimal unitPrice;
    String image;
}
