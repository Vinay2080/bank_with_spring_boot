package org.example.bankwithspringboot.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatedResponse {
    private String username;
    private String email;
}
