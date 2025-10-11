package org.example.restaurantProject.mapper;

import org.example.restaurantProject.dto.request.users.UserUpdateEmailRequest;
import org.example.restaurantProject.dto.request.users.UserUpdatePasswordRequest;
import org.example.restaurantProject.dto.request.users.UserUpdateUsernameRequest;
import org.example.restaurantProject.dto.response.users.UserUpdatedResponse;
import org.example.restaurantProject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // update username
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "newUsername", target = "username")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stakeholder", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void dtoToUsernameUpdate(UserUpdateUsernameRequest request, @MappingTarget User user);

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stakeholder", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "newEmail")
    void dtoToEmailUpdate(UserUpdateEmailRequest request, @MappingTarget User user);

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stakeholder", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "password", source = "newPassword")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void dtoToPasswordUpdate(UserUpdatePasswordRequest request, @MappingTarget User user);

    // common response for update
    UserUpdatedResponse entityUserUpdateResponse(User user);

}
