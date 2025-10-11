package org.example.restaurantProject.mapper;

import org.example.restaurantProject.dto.request.Authentications.AuthenticationRequest;
import org.example.restaurantProject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stakeholder", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User authRequestToUser(AuthenticationRequest request);
}
