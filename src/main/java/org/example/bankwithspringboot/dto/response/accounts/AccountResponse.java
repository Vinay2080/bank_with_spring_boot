package org.example.bankwithspringboot.dto.response.accounts;

import lombok.*;
import org.example.bankwithspringboot.enums.AccountType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private Double balance;
    private AccountType accountType;

}
