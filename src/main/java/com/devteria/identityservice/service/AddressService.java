package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.*;
import com.devteria.identityservice.dto.response.AddressResponse;
import com.devteria.identityservice.dto.response.UserHasAvatarResponse;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.AddressEntity;
import com.devteria.identityservice.entity.UsersEntity;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.mapper.AddressMapper;
import com.devteria.identityservice.repository.AddressRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AddressRepository addressRepository;
    AddressMapper addressMapper;
    UserRepository userRepository;
    IStorageService storageService;
    PasswordEncoder passwordEncoder;

    public List<AddressResponse> getAll(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Long userId = user.getUserId();
        return addressRepository.findByUserId(userId).stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    public AddressResponse create(AddressCreationRequest request){
        AddressEntity address = addressMapper.toAddressEntity(request);
        //get the user id from authentication
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        address.setUserId(user.getUserId());
        address = addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse update(Long addressId, AddressUpdateRequest request) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        addressMapper.updateAddressEntity(address, request);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    public AddressResponse delete(Long addressId) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        addressRepository.delete(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse getOne(Long addressId) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse getAddress(Long addressId) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        return addressMapper.toAddressResponse(address);
    }

    public UserHasAvatarResponse getMyInfo() {
    var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return UserHasAvatarResponse.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .build();

    }

    public UserHasAvatarResponse updateUser(UserUpdateFileRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String avatar = storageService.storeFile(request.getAvatar());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        try {
            user.setUpdatedAt(dateFormat.parse(formattedDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        user.setFullname(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAvatar(avatar);

        userRepository.save(user);
        return UserHasAvatarResponse.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .build();
    }

    public String changePassword(UserChangePasswordRequest request) {
        //get old password
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UsersEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean matches = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
        String oldPassword = user.getPassword();
        if (!matches) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        //check if new password is the same as old password
        if (oldPassword.equals(request.getNewPassword())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_SAME_OLD_PASS);
        }

        //check if new password is not  same as confirm password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_NOT_SAME_CONFIRM_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password has been changed";
    }
}
