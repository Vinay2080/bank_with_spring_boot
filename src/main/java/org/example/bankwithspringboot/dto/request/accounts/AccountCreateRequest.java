package org.example.bankwithspringboot.dto.request.accounts;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bankwithspringboot.enums.AccountType;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AccountCreateRequest {

    private static final long MAX_USER_ID = 9999999999L;

    @NotNull(message = "Initial balance is required")
    @PositiveOrZero(message = "Initial balance must be positive")
    @DecimalMax(value = "1000000.00", message = "Initial balance cannot exceed 1,000,000.00")
    @Digits(integer = 7, fraction = 2, message = "Initial balance must have up to 7 integer and 2 decimal places")
    private Double initialBalance;

    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id cannot be negative")
    @Max(value = MAX_USER_ID, message = "User ID is too large")
    private Long userId;

    @NotNull(message = "Account type cannot be null")
    private AccountType accountType;

    @Pattern(regexp = "^\\d{4}$", message = "password should consist of 4 digits")
    private String password;
}
