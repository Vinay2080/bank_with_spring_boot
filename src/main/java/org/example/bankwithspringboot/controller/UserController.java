package org.example.bankwithspringboot.controller;

import lombok.Getter;
import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.apiResponse.ResponseUtility;
import org.example.bankwithspringboot.dto.request.users.UserUpdateEmailRequest;
import org.example.bankwithspringboot.dto.request.users.UserUpdatePasswordRequest;
import org.example.bankwithspringboot.dto.request.users.UserUpdateUsernameRequest;
import org.example.bankwithspringboot.dto.request.users.userDeleteRequest;
import org.example.bankwithspringboot.dto.response.users.UserUpdatedResponse;
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
    @PutMapping("/update/Username")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updateUsername(@RequestBody UserUpdateUsernameRequest request) {
        UserUpdatedResponse updatedUsername = service.updateUsername(request);
        return ResponseUtility.success("username updated successfully", HttpStatus.OK, updatedUsername);
        // change the status code
    }

    @PutMapping("/update/email")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updateEmail(@RequestBody UserUpdateEmailRequest request) {
        UserUpdatedResponse userUpdatedResponse = service.updateEmail(request);
        return ResponseUtility.success("email updated successfully", HttpStatus.OK, userUpdatedResponse);
    }

    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse<UserUpdatedResponse>> updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        UserUpdatedResponse userUpdatedResponse = service.updatePassword(request);
        return ResponseUtility.success("password updated successfully", HttpStatus.OK, userUpdatedResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@RequestBody userDeleteRequest request) {
        boolean accountDeleted = service.deleteUser(request);
        if (!accountDeleted) {
            return ResponseUtility.error("Account not found", HttpStatus.NOT_FOUND);
        }
        return ResponseUtility.success("Account deleted successfully", HttpStatus.OK, null);
    }
}
