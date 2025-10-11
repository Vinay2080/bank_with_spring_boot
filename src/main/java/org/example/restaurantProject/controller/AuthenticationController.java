package org.example.restaurantProject.controller;

import jakarta.validation.Valid;
import org.example.restaurantProject.dto.apiResponse.ApiResponse;
import org.example.restaurantProject.dto.apiResponse.ResponseUtility;
import org.example.restaurantProject.dto.request.Authentications.AuthenticationRequest;
import org.example.restaurantProject.dto.response.Authentications.AuthResponse;
import org.example.restaurantProject.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@Valid @RequestBody AuthenticationRequest request) {
        AuthResponse jwtToken = service.registerUser(request);
        return ResponseUtility.success("user registered successfully ", HttpStatus.CREATED, jwtToken);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@Valid @RequestBody AuthenticationRequest request){
        AuthResponse jwtToken = service.LoginUser(request);
        return ResponseUtility.success("login successful", HttpStatus.OK, jwtToken);

    }

}
