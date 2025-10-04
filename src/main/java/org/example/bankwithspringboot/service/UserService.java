package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.bankwithspringboot.dto.request.users.*;
import org.example.bankwithspringboot.dto.response.users.UserResponse;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
import org.example.bankwithspringboot.enums.UserStatus;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.mapper.UserMapper;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
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
    public UserResponse registerUser(UserRegisterRequest request) {

        User user = userMapper.dtoToUserRegister(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return userMapper.entityToUserResponse(user);
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
