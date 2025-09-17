package org.example.bankwithspringboot.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
