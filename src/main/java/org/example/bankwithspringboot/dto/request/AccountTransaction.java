package org.example.bankwithspringboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// add valid
// something for entities too

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    private String accountNumber;
    private Double amount;
}
