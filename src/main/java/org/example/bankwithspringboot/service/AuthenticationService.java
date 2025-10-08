package org.example.bankwithspringboot.service;

import org.example.bankwithspringboot.dto.request.Authentications.AuthRequest;
import org.example.bankwithspringboot.dto.response.Authentications.AuthResponse;
import org.example.bankwithspringboot.exception.ResourceAlreadyExistsException;
import org.example.bankwithspringboot.mapper.AuthenticationMapper;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;

    public AuthenticationService(UserRepository userRepository, AuthenticationMapper authenticationMapper,
                                 PasswordEncoder passwordEncoder, JwtServices jwtServices) {

        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
    }

    public AuthResponse registerUser(AuthRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException("User with given email already exits");
        }
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
        return new AuthResponse(token, user.getEmail(), user.getUsername());
    }
}
