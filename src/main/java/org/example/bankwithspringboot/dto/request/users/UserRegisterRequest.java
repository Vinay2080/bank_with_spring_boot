package org.example.bankwithspringboot.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterRequest {
    @NotBlank(message = "should contain a name, field cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters and spaces")
    @Size(min = 2, max = 100, message = "name must be between 4 to 100 letters")
    private String name;

    @NotBlank(message = "should contain a username, field cannot be blank")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username must start with a letter and can only contain letters, numbers, and underscores")
    @Size(min = 2, max = 100, message = "name must be between 4 to 100 letters")
    private String username;

    @NotBlank(message = "should contain a password, field cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$",
            message = "Password must contain at least one letter and one number")
    @Size(min = 4, max = 20, message = "minimum size of password should be 4")
    private String password;

    @NotBlank(message = "should contain a email, field cannot be blank")
    @Email(message = "enter a valid email")
    private String email;

    @NotBlank(message = "should contain a phone number, field cannot be blank")
    @Pattern(regexp = "^[1-9]\\\\d{9}$", message = "phone number should only consists of 10 digits")
    private String phoneNumber;
}
