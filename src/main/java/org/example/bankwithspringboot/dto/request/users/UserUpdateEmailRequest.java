package org.example.bankwithspringboot.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateEmailRequest {

    @NotBlank(message = "should contain a email, field cannot be blank")
    @Email(message = "enter a valid email")
    private String oldEmail;

    @NotBlank(message = "should contain a email, field cannot be blank")
    @Email(message = "enter a valid email")
    private String newEmail;
}
