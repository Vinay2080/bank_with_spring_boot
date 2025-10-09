package org.example.bankwithspringboot.dto.request.Authentications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest implements Serializable {
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "username only consists letters, numbers and '_'")
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank
    private String password;
}