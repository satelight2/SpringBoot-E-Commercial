package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.*;
import com.devteria.identityservice.dto.response.AddressResponse;
import com.devteria.identityservice.dto.response.UserHasAvatarResponse;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserAccountController {
    AddressService addressService;
    @PostMapping("/address")
    ApiResponse<AddressResponse> createAddress(@Valid  @RequestBody AddressCreationRequest request){
        return ApiResponse.<AddressResponse>builder()
                .message("Address has been created")
                .result(addressService.create(request))
                .build();
    }

    @GetMapping("/address")
    ApiResponse<List<AddressResponse>> getAddresses(){
        return ApiResponse.<List<AddressResponse>>builder()
                .message("Addresses")
                .result(addressService.getAll())
                .build();
    }

    @PutMapping("/address/{addressId}")
    ApiResponse<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody AddressUpdateRequest request){
        return ApiResponse.<AddressResponse>builder()
                .message("Address has been updated")
                .result(addressService.update(addressId, request))
                .build();
    }

    @DeleteMapping("/address/{addressId}")
    ApiResponse<AddressResponse> deleteAddress(@PathVariable Long addressId){
        return ApiResponse.<AddressResponse>builder()
                .message("Address has been deleted")
                .result(addressService.delete(addressId))
                .build();
    }

    @GetMapping("/address/{addressId}")
    ApiResponse<AddressResponse> getAddress(@PathVariable Long addressId){
        return ApiResponse.<AddressResponse>builder()
                .message("Address")
                .result(addressService.getAddress(addressId))
                .build();
    }

    @GetMapping("")
    ApiResponse<UserHasAvatarResponse> getMyInfo(){
        return ApiResponse.<UserHasAvatarResponse>builder()
                .message("My Info")
                .result(addressService.getMyInfo())
                .build();
    }


    //update user info login
    @PutMapping("")
    ApiResponse<UserHasAvatarResponse> updateUser(@ModelAttribute UserUpdateFileRequest request){
        return ApiResponse.<UserHasAvatarResponse>builder()
                .message("User has been updated")
                .result(addressService.updateUser(request))
                .build();
    }

    //change pass
    @PutMapping("/change-password")
    ApiResponse<String> changePassword(@RequestBody UserChangePasswordRequest request){
        return ApiResponse.<String>builder()
                .result(addressService.changePassword(request))
                .build();
    }
}
