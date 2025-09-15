package org.example.bankwithspringboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountRequest {
    private Double initialBalance;
    private Long userId;
    private String accountType;
}