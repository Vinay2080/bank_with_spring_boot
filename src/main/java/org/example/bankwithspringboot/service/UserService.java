package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.bankwithspringboot.dto.request.users.*;
import org.example.bankwithspringboot.dto.response.users.UserResponse;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService {

    // user new password and old password must not be the same applied for all of them.
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        user.getAccounts().forEach(account -> account.setUser(user));
        User saved = userRepository.save(user);
        return new UserResponse(saved.getName(), saved.getUsername(), saved.getPhoneNumber(), saved.getEmail());
    }

    public UserResponse loginUser(UserLoginAndDeleteRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("user with this email does not exists.\n enter a valid email."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }
        return new UserResponse(
                user.getUsername(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber()
        );
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
        if (!   user.getPassword().equals(request.getOldPassword())){
            throw new IllegalArgumentException("old password does not match.");
        }
        user.setPassword(request.getNewPassword());
        return new UserUpdatedResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public boolean deleteUser(UserLoginAndDeleteRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ResourceNotFoundException("No user with this username is available.\nEnter a valid email."));

        if (!user.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("enter password does not match.\nEnter a valid password");
        }
        userRepository.deleteUserByEmail(request.getEmail());
        return true;
    }
}
