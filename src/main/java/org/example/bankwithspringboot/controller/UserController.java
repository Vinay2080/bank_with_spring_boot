package org.example.bankwithspringboot.controller;

import lombok.Getter;
import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.request.users.*;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
import org.example.bankwithspringboot.dto.response.users.UserResponse;
import org.example.bankwithspringboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Getter
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // add get al users
    //  by name
    // login by phone number

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerUser(@RequestBody UserRegisterRequest request) {
        UserResponse savedUser = service.registerUser(request);
        return response("user registered successfully ", HttpStatus.CREATED, savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(@RequestBody UserLoginAndDeleteRequest request) {
        UserResponse loggedInUser = service.loginUser(request);
        return response("user logged in successfully", HttpStatus.ACCEPTED, loggedInUser);
    }

    @PutMapping("/update/Username")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updateUsername(@RequestBody UserUpdateUsernameRequest request) {
        UserUpdatedResponse updatedUsername = service.updateUsername(request);
        return response("username updated successfully",HttpStatus.OK, updatedUsername);
       // change the status code
    }

    @PutMapping("/update/email")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updateEmail(@RequestBody UserUpdateEmailRequest request) {
        UserUpdatedResponse userUpdatedResponse = service.updateEmail(request);
        return response("email updated successfully", HttpStatus.OK, userUpdatedResponse);
    }

    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        UserUpdatedResponse userUpdatedResponse = service.updatePassword(request);
        return response("password updated successfully", HttpStatus.OK, userUpdatedResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@RequestBody UserLoginAndDeleteRequest request) {
        boolean accountDeleted = service.deleteUser(request);
        if (!accountDeleted){
            return response("User not found", HttpStatus.NOT_FOUND, null);
        }
        return response("Account deleted successfully", HttpStatus.OK, null);
    }

    private <T> ResponseEntity<ApiResponse<T>> response(String message, HttpStatus status, T data){
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
