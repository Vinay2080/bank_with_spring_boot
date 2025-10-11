package org.example.restaurantProject.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.restaurantProject.dto.request.users.UserUpdateEmailRequest;
import org.example.restaurantProject.dto.request.users.UserUpdatePasswordRequest;
import org.example.restaurantProject.dto.request.users.UserUpdateUsernameRequest;
import org.example.restaurantProject.dto.request.users.userDeleteRequest;
import org.example.restaurantProject.dto.response.users.UserUpdatedResponse;
import org.example.restaurantProject.enums.UserStatus;
import org.example.restaurantProject.exception.ResourceNotFoundException;
import org.example.restaurantProject.mapper.UserMapper;
import org.example.restaurantProject.model.User;
import org.example.restaurantProject.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService {

    // user new password and old password must not be the same applied for all of them.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserUpdatedResponse updateUsername(UserUpdateUsernameRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User with this username does not exists.\nEnter a valid username.\n"));

        userMapper.dtoToUsernameUpdate(request, user);
        return userMapper.entityUserUpdateResponse(user);
    }

    @Transactional
    public UserUpdatedResponse updateEmail(UserUpdateEmailRequest request) {

        User user = userRepository.findByEmail(request.getOldEmail()).orElseThrow(() -> new ResourceNotFoundException("User with this email does not exists.\nEnter a valid email"));

        userMapper.dtoToEmailUpdate(request, user);

        return userMapper.entityUserUpdateResponse(user);
    }

    @Transactional
    public UserUpdatedResponse updatePassword(UserUpdatePasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("user with this username does not exists.\nEnter a valid email"));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("old password does not match.");
        }
        if (!request.getNewPassword().equals(request.getNewPassword2())) {
            throw new IllegalArgumentException("the password does not match with above password");
        }
        userMapper.dtoToPasswordUpdate(request, user);
        return userMapper.entityUserUpdateResponse(user);
    }

    @Transactional
    public boolean deleteUser(userDeleteRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No user with this username is available.\nEnter a valid email."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }
        if (user.getStatus() == UserStatus.INACTIVE) {
            return false;
        }
        user.setStatus(UserStatus.INACTIVE);
        return true;
    }
}
