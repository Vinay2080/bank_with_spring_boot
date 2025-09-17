package org.example.bankwithspringboot.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginAndDeleteRequest {
    private String email;
    private String password;
}
