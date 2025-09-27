package org.example.bankwithspringboot.dto.response.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bankwithspringboot.enums.AccountType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private Double balance;
    private AccountType accountType;

}
