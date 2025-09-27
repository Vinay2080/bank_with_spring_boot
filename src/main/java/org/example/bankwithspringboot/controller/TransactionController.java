package org.example.bankwithspringboot.controller;

import jakarta.validation.Valid;
import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.apiResponse.ResponseUtility;
import org.example.bankwithspringboot.dto.request.transactions.TransactionRequest;
import org.example.bankwithspringboot.dto.response.transaction.TransactionResponse;
import org.example.bankwithspringboot.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponse>> depositMoney(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse deposited = transactionService.depositMoney(request);
        return ResponseUtility.success("amount deposited successfully", HttpStatus.OK, deposited);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponse>> creditMoney(@Valid @RequestBody TransactionRequest request){
        TransactionResponse credited = transactionService.creditMoney(request);
        return ResponseUtility.success("amount withdrawn successfully", HttpStatus.OK, credited);
    }
}
