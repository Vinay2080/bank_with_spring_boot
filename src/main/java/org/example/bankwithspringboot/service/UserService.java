package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.bankwithspringboot.dto.request.users.*;
import org.example.bankwithspringboot.dto.response.users.UserResponse;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
import org.example.bankwithspringboot.enums.UserStatus;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.getAccounts().forEach(account -> account.setUser(user));
        User saved = userRepository.save(user);
        return new UserResponse(saved.getName(), saved.getUsername(), saved.getPhoneNumber(), saved.getEmail());
    }

    @Transactional
    public UserUpdatedResponse updateUsername(UserUpdateUsernameRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User with this username does not exists.\nEnter a valid username.\n"));
        user.setUsername(request.getNewUsername());

        return new UserUpdatedResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public UserUpdatedResponse updateEmail(UserUpdateEmailRequest request) {

        User user = userRepository.findByEmail(request.getOldEmail()).orElseThrow(() -> new ResourceNotFoundException("User with this email does not exists.\nEnter a valid email"));

        user.setEmail(request.getNewEmail());
        return new UserUpdatedResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public UserUpdatedResponse updatePassword(UserUpdatePasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("user with this username does not exists.\nEnter a valid email"));
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new IllegalArgumentException("old password does not match.");
        }
        user.setPassword(request.getNewPassword());
        return new UserUpdatedResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public boolean deleteUser(userDeleteRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No user with this username is available.\nEnter a valid email."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }
        if(user.getStatus() == UserStatus.INACTIVE){
            return false;
        }
        user.setStatus(UserStatus.INACTIVE);
        return true;
    }
}
