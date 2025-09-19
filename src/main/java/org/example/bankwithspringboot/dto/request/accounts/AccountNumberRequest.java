package org.example.bankwithspringboot.dto.request.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AccountNumberRequest {
    private String accountNumber;
}
