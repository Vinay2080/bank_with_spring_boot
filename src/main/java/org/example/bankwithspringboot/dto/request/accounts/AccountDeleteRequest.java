package org.example.bankwithspringboot.dto.request.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDeleteRequest {
    private String accountNumber;
}
