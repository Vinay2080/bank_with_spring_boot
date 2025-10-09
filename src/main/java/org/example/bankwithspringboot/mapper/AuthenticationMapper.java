package org.example.bankwithspringboot.mapper;

import org.example.bankwithspringboot.dto.request.Authentications.AuthRegisterRequest;
import org.example.bankwithspringboot.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stakeholder", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    User authRequestToUser(AuthRegisterRequest request);
}
