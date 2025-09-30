package org.example.bankwithspringboot.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String message;

    private HttpStatus status;
}
