package org.example.bankwithspringboot.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String username;
    private String phoneNumber;
    private String email;
}
