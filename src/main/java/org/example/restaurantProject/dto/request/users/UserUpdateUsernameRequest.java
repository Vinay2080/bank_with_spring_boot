package org.example.restaurantProject.dto.request.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateUsernameRequest {

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username must start with a letter and can only contain letters, numbers, and underscores")
    @NotBlank(message = "should contain a username, field cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$",
            message = "Password must contain at least one letter and one number")
    @Size(min = 4, max = 20, message = "minimum size of password should be 4")
    private String username;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username must start with a letter and can only contain letters, numbers, and underscores")
    @NotBlank(message = "should contain a username, field cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$",
            message = "Password must contain at least one letter and one number")
    @Size(min = 4, max = 20, message = "minimum size of password should be 4")
    private String newUsername;
}
