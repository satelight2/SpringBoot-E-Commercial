package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UsersEntity toUserEntity(UserCreationRequest request);

    UserResponse toUserResponse(UsersEntity user);

    @Mapping(target = "roles", ignore = true)
    void updateUsersEntity(@MappingTarget UsersEntity user, UserUpdateRequest request);
}
