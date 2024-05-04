package com.devteria.identityservice.dto.request;

import com.devteria.identityservice.validator.DobConstraint;
import com.devteria.identityservice.validator.PhoneNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4,message = "USERNAME_INVALID")
    String username;
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    @Size(min = 6, message = "INVALID_PASSWORD")
    String confirmPassword;
    @NotNull(message = "FULLNAME_REQUIRED")
    String fullname;
    @NotNull(message = "EMAIL_REQUIRED")
    String email;
    @PhoneNumber
    String phone;
    @NotNull(message ="ADDRESS_REQUIRED" )
    String address;

}
