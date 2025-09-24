package org.example.bankwithspringboot.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private String accountNumber;
    private Double currentBalance;
}
