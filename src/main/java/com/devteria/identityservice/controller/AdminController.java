package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.ProductRequest;
import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.dto.response.RoleResponse;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.service.RoleService;
import com.devteria.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminController {
    UserService userService;
    RoleService roleService;

    @PostMapping("/sign-up")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        log.info("Controller: create User");
        return ApiResponse.<UserResponse>builder()
                .message("User has been created")
                .build();
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }


    @PostMapping("/users/{userId}/role/{roleId}")
    ApiResponse<UserResponse> addRoleToUser(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.addRoleToUser(userId, roleId))
                .build();
    }

    @DeleteMapping("/users/{userId}/role/{roleId}")
    ApiResponse<UserResponse> deleteRoleFromUser(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.deleteRoleFromUser(userId, roleId))
                .build();
    }

    //users/{userId}	PUT	Khóa / Mở khóa người dùng
    @PutMapping("/users/{userId}")
    ApiResponse<UserResponse> lockOrUnlockUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.lockOrUnlockUser(userId))
                .build();
    }
    // /api.myservice.com/v1/admin/roles	GET	Lấy về danh sách quyền
    @GetMapping("/roles")
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }


    @GetMapping("/users/search")
    ApiResponse<List<UserResponse>> searchUser(@RequestParam("search") String name){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.searchUser(name))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    // http://localhost:8080/api.myservice.com/v1/admin/add-role/4 to add role with id 4 to user with id 1
    @PutMapping("/add-role-user-for-user/{userId}")
    ApiResponse<UserResponse> addRoleToUser(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.addRoleToUser(userId, 1L))
                .build();

    }


}
