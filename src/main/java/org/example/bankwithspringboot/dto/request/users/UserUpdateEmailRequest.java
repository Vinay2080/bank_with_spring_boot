package org.example.bankwithspringboot.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateEmailRequest {
    private String oldEmail;
    private String newEmail;
}
