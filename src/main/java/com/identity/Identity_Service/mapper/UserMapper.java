package com.identity.Identity_Service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.identity.Identity_Service.dto.request.UserCreationRequest;
import com.identity.Identity_Service.dto.request.UserUpdateRequest;
import com.identity.Identity_Service.dto.response.UserResponse;
import com.identity.Identity_Service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest req);
    void updateUser( @MappingTarget User user, UserUpdateRequest req);
    UserResponse toUserResponse(User user);
}

