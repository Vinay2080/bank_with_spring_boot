package org.example.bankwithspringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private Double balance;
    private String accountType;

}
