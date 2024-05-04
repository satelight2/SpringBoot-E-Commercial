package com.devteria.identityservice.dto.response;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartItemResponse {
    long shoppingCartItemId;
    Long shoppingCartId;
    Long productId;
    Integer orderQuantity;
}
