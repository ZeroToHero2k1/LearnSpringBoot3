package com.zerotohero.crudapp.mapper;

import com.zerotohero.crudapp.dto.request.RoleRequest;
import com.zerotohero.crudapp.dto.response.RoleResponse;
import com.zerotohero.crudapp.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);
}
