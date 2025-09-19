package org.example.bankwithspringboot.dto.response.accounts;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private Double balance;
    private String accountType;

}
