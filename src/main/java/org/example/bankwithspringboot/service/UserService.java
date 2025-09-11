package org.example.bankwithspringboot.service;

import lombok.Getter;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User registeruser(User user) {
        user.setId(null);
        user.getAccounts().forEach(account -> account.setUser(user));
        return repository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> UpdateUsername(String username, String newUsername) {
        return repository.findByUsername(username)
                .map(user -> {
                    user.setUsername(newUsername);
                    return repository.save(user);
                });
    }

    public Optional<User> UpdateEmail(String newEmail, String username) {
        return repository
                .findByUsername(username)
                .map(var ->
                {
                    var.setEmail(newEmail);
                    return repository.save(var);
                });
    }

    public Optional<User> updatePassword(String password, String username) {
        return repository
                .findByUsername(username)
                .map(var ->{
                    var.setPassword(password);
                    return repository.save(var);
                });
    }
}
