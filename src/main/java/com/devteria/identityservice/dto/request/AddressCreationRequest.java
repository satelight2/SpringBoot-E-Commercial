package com.devteria.identityservice.dto.request;

import com.devteria.identityservice.validator.PhoneNumber;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressCreationRequest {
    long addressId;
    @NotNull
    @NotEmpty
    String fullAddress;
    @NotNull
    @NotEmpty
    @PhoneNumber
    String phone;
    @NotNull
    @NotEmpty
    String receiveName;
}
