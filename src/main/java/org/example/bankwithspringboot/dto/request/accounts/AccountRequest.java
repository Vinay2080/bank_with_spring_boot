package org.example.bankwithspringboot.dto.request.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountRequest {
    private Double initialBalance;
    private Long userId;
    private String accountType;
}