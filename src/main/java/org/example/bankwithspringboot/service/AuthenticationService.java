package org.example.bankwithspringboot.service;

import org.example.bankwithspringboot.dto.request.Authentications.AuthenticationRequest;
import org.example.bankwithspringboot.dto.response.Authentications.AuthResponse;
import org.example.bankwithspringboot.exception.ResourceAlreadyExistsException;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.mapper.AuthenticationMapper;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationMapper authenticationMapper,
                                 PasswordEncoder passwordEncoder, JwtServices jwtServices, AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registerUser(AuthenticationRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new ResourceAlreadyExistsException("User with given username already exits");
        }
        User user = authenticationMapper.authRequestToUser(request);
        // encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // persist user
        user = userRepository.save(user);
        // generate JWT token for the new user
        String token = jwtServices.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse LoginUser(AuthenticationRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }catch (BadCredentialsException exception){
            throw new BadCredentialsException("wrong username or password");
        }
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new ResourceNotFoundException("user with this username does not exists"));

        String token = jwtServices.generateToken(user);

        return new AuthResponse(token);
    }
}
