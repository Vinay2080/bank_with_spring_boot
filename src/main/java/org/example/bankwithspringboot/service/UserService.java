package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registeruser(User user) {
        user.setId(null);
        user.getAccounts().forEach(account -> account.setUser(user));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> UpdateUsername(String username, String newUsername) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setUsername(newUsername);
                    return userRepository.save(user);
                });
    }

    public Optional<User> UpdateEmail(String newEmail, String username) {
        return userRepository
                .findByUsername(username)
                .map(var ->
                {
                    var.setEmail(newEmail);
                    return userRepository.save(var);
                });
    }

    public Optional<User> updatePassword(String password, String username) {
        return userRepository
                .findByUsername(username)
                .map(var ->{
                    var.setPassword(password);
                    return userRepository.save(var);
                });
    }

    @Transactional
    public Optional<User> deleteUserByUsername(User user) {
        return userRepository.findByUsername(user.getUsername()).map(foundUser->{
            userRepository.deleteByUsername(user.getUsername());
            return foundUser;
        });
    }
}
