package org.example.bankwithspringboot.mapper;

import org.example.bankwithspringboot.dto.request.Authentications.AuthRegisterRequest;
import org.example.bankwithspringboot.dto.request.Authentications.AuthLoginRequest;
import org.example.bankwithspringboot.model.User;
import org.mapstruct.*;

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

    User toEntity(AuthLoginRequest authLoginRequest);

//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "timeStamp", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "stakeholder", ignore = true)
//    @Mapping(target = "phoneNumber", ignore = true)
//    @Mapping(target = "name", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "email", ignore = true)
//    @Mapping(target = "authorities", ignore = true)
//    @Mapping(target = "accounts", ignore = true)
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    User partialUpdate(AuthLoginRequest authLoginRequest, @MappingTarget User user);
}
