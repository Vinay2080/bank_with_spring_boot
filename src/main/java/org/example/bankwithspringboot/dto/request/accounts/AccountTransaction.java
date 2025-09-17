package org.example.bankwithspringboot.dto.request.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
// add valid
// something for entities too

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    private String accountNumber;
    private Double amount;
}
