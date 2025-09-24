package org.example.bankwithspringboot.dto.request.transactions;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^2025\\d{8}$", message = "Account number must start with 2025 and be 12 digits long")
    @Size(min = 12, max = 12, message = "Account number must be exactly 12 digits")
    String accountNumber;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @DecimalMax(value = "1000000.00", message = "Amount cannot exceed 1,000,000.00")
    @Digits(integer = 7, fraction = 2, message = "Amount must have up to 7 integer and 2 decimal places")
    Double amount;
}
