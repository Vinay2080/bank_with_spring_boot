package org.example.bankwithspringboot.controller;

import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.request.accounts.AccountCreateRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountNumberRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountTransactionRequest;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody AccountCreateRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        ApiResponse<AccountResponse> response = ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("account created successfully")
                .data(accountResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get/all_accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> list = accountService.getAllAccounts();
        ApiResponse<List<AccountResponse>> response = ApiResponse.<List<AccountResponse>>builder()
                .success(true)
                .message("Accounts fetched successfully")
                .data(list)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/by_account_number")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByAccountNumber(@RequestBody AccountNumberRequest request) {
        AccountResponse response = accountService.findAccountByAccountNumber(request);
        ApiResponse<AccountResponse> apiResponse = ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("account fetched successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> depositMoney(@RequestBody AccountTransactionRequest accountTransactionRequest) {
        AccountResponse accountUpdated = accountService.depositMoney(accountTransactionRequest);
        ApiResponse<AccountResponse> apiResponse = ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("transaction completed successfully")
                .data(accountUpdated)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/credit")
    public ResponseEntity<ApiResponse<AccountResponse>> creditMoney(@RequestBody AccountTransactionRequest accountTransactionRequest) {
        AccountResponse accountUpdated = accountService.creditMoney(accountTransactionRequest);
        ApiResponse<AccountResponse> apiResponse = ApiResponse.<AccountResponse>builder()
                .success(true)
                .message("amount credited successfully")
                .data(accountUpdated)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> deleteAccount(@RequestBody AccountNumberRequest request) {
        boolean accountDeleted = accountService.removeAccount(request);
        if (accountDeleted) {
            ApiResponse<Object> apiResponse = ApiResponse.builder()
                    .success(true)
                    .message("Account deleted successfully")
                    .build();
            return ResponseEntity.ok(apiResponse);
        }
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(false)
                .message("Account not found or could not be deleted")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
