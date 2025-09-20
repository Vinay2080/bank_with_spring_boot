package org.example.bankwithspringboot.dto.request.accounts;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
// add valid
// something for entities too

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionRequest {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^2025\\d{8}$", message = "Account number must start with 2025 and be 12 digits long")
    @Size(min = 12, max = 12, message = "Account number must be exactly 12 digits")
    @Digits(integer = 12, fraction = 0, message = "Account number must contain only digits")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @DecimalMax(value = "1000000.00", message = "Amount cannot exceed 1,000,000.00")
    @Digits(integer = 7, fraction = 2, message = "Amount must have up to 7 integer and 2 decimal places")
    private Double amount;
}
