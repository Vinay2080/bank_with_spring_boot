package org.example.bankwithspringboot.controller;

import lombok.Getter;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Getter
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = service.registeruser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<User> loggedInUser = service.loginUser(user.getEmail(), user.getPassword());
        return loggedInUser.map(value -> ResponseEntity.ok("Login successful! Welcome " + value.getName())).orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }

    @PutMapping("/update/Username/{username}")
    public ResponseEntity<User> updateUsername(@PathVariable String username, @RequestBody String newUsername) {
        newUsername = newUsername.replace("\"", "");
        Optional<User> updatedUsername = service.UpdateUsername(username, newUsername);
        return updatedUsername
                .map(ResponseEntity::ok )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/email/{username}")
    public ResponseEntity<User> updateEmail(@RequestBody String newEmail, @PathVariable String username) {
        newEmail = newEmail.replace("\"", "");
        Optional<User> updatedEmail = service.UpdateEmail(newEmail, username);
        return updatedEmail.map(
                        ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()
                );
    }
}
