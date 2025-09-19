package org.example.bankwithspringboot.controller;

import lombok.Getter;
import org.example.bankwithspringboot.dto.request.users.*;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
import org.example.bankwithspringboot.dto.response.users.UserResponse;
import org.example.bankwithspringboot.service.UserService;
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
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest request) {
        UserResponse savedUser = service.registerUser(request);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginAndDeleteRequest request) {
        UserResponse loggedInUser = service.loginUser(request);
        return ResponseEntity.ok(loggedInUser);
    }

    @PutMapping("/update/Username")
    public ResponseEntity<UserUpdatedResponse> updateUsername(@RequestBody UserUpdateUsernameRequest request) {
        UserUpdatedResponse updatedUsername = service.updateUsername(request);
        return ResponseEntity.ok(updatedUsername);
       // change the status code
    }

    @PutMapping("/update/email")
    public ResponseEntity<UserUpdatedResponse> updateEmail(@RequestBody UserUpdateEmailRequest request) {
        UserUpdatedResponse response = service.updateEmail(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/password")
    public ResponseEntity<UserUpdatedResponse> updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        UserUpdatedResponse response = service.updatePassword(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserLoginAndDeleteRequest request) {
        boolean accountDeleted = service.deleteUser(request);
        if (!accountDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("User deleted successfully");
    }
}
