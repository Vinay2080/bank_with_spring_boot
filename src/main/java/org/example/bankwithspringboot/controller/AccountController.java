package org.example.bankwithspringboot.controller;

import jakarta.validation.Valid;
import org.example.bankwithspringboot.dto.apiResponse.ApiResponse;
import org.example.bankwithspringboot.dto.apiResponse.ResponseUtility;
import org.example.bankwithspringboot.dto.request.accounts.AccountCreateRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountNumberRequest;
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

    // controller to create an account
    // stores password and username to a database for further use by spring security
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ResponseUtility.success("Account created successfully", HttpStatus.CREATED, accountResponse);
    }

    // a user can get a list of all his accounts
    @GetMapping("/get/all_accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> list = accountService.getAllAccounts();
        return ResponseUtility.success("accounts fetched successfully", HttpStatus.FOUND, list);
    }

    @GetMapping("/get/by_account_number")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByAccountNumber(@Valid @RequestBody AccountNumberRequest request) {
        AccountResponse response = accountService.findAccountByAccountNumber(request);
        return ResponseUtility.success("account fetched successfully",HttpStatus.FOUND, response);
    }

    // here we don't delete an account as it will lead to deletion of transaction which will cause calculation errors bank
    // the account is set as closed status instead
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> deleteAccount(@Valid @RequestBody AccountNumberRequest request) {
        boolean accountDeleted = accountService.removeAccount(request);
        if (accountDeleted) {
            return ResponseUtility.success("amount credited successfully", HttpStatus.OK, null);
        }
        return ResponseUtility.error("account not found", HttpStatus.NOT_FOUND);
    }
}
// this is new branch
