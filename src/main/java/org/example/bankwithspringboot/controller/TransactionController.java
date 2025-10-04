package org.example.bankwithspringboot.controller;

import jakarta.validation.Valid;
import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.apiResponse.ResponseUtility;
import org.example.bankwithspringboot.dto.request.transactions.TransactionRequest;
import org.example.bankwithspringboot.dto.response.transaction.TransactionResponse;
import org.example.bankwithspringboot.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // for transaction between two accounts
    @PostMapping("/transferMoney")
    public ResponseEntity<ApiResponse<TransactionResponse>> transferMoney(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.transferMoney(request);
        return ResponseUtility.success("Transaction successful", HttpStatus.OK, response);
    }
}
