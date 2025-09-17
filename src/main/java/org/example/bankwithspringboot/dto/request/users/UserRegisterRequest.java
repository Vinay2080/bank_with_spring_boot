package org.example.bankwithspringboot.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
