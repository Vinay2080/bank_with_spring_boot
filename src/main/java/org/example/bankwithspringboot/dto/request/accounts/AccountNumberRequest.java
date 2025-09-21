package org.example.bankwithspringboot.dto.request.accounts;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AccountNumberRequest {
    @NotNull(message = "account Number cannot be null")
    @Pattern(regexp = "^2025\\d{8}$", message = "Account Number starts with 2025 and is 12 digits long")
    @Digits(integer = 12, fraction = 0, message = "Account number must contain only digits")
    private String accountNumber;
}
