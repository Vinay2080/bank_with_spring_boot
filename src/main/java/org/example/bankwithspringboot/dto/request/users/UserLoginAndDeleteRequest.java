package org.example.bankwithspringboot.dto.request.users;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginAndDeleteRequest {

    @Email(message = "enter a valid email")
    @NotNull(message = "email cannot be null")
    private String email;

    @NotBlank(message = "password cannot blank or containing only whitespaces")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$",
            message = "Password must contain at least one letter and one number")
    @Size(min = 4, max = 20, message = "minimum size of password should be 4")
    private String password;
}
