package com.zerotohero.crudapp.mapper;

import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
