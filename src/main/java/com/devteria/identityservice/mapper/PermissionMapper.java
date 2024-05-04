package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.PermissionRequest;
import com.devteria.identityservice.dto.response.PermissionResponse;
import com.devteria.identityservice.entity.PermissionsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionsEntity toPermissionsEntity(PermissionRequest request);
    PermissionResponse toPermissionResponse(PermissionsEntity permission);
}
