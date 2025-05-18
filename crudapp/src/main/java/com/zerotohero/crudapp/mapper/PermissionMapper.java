package com.zerotohero.crudapp.mapper;

import com.zerotohero.crudapp.dto.request.PermissionRequest;
import com.zerotohero.crudapp.dto.response.PermissionResponse;
import com.zerotohero.crudapp.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionReponse(Permission permission);
    Permission toPermission(PermissionRequest request);
}
