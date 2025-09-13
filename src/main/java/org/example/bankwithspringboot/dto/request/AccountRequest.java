package org.example.bankwithspringboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRequest {
    private Double initialBalance;
    private Long userId;
    private String accountType;
}