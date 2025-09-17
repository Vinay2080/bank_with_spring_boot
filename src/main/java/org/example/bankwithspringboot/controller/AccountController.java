package org.example.bankwithspringboot.controller;

import org.example.bankwithspringboot.dto.request.accounts.AccountDeleteRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountTransaction;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        Account accountCreated = accountService.createAccount(request);

        AccountResponse response = new AccountResponse();
        response.setAccountNumber(accountCreated.getAccountNumber());
        response.setBalance(accountCreated.getBalance());
        response.setAccountType(accountCreated.getAccountType());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all_accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/get/by_account_number/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountByAccountNumber(@PathVariable String accountNumber) {
        return accountService.findAccountByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountResponse> depositMoney(@RequestBody AccountTransaction accountTransaction) {
        AccountResponse accountUpdated = accountService.depositMoney(accountTransaction);
        return ResponseEntity.ok(accountUpdated);
    }

    @PutMapping("/credit")
    public ResponseEntity<AccountResponse> creditMoney(@RequestBody AccountTransaction accountTransaction) {
        AccountResponse accountUpdated = accountService.creditMoney(accountTransaction);
        return ResponseEntity.ok(accountUpdated);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody AccountDeleteRequest request) {
        boolean accountDeleted = accountService.removeAccount(request);
        if (accountDeleted){
            return ResponseEntity.ok("account deleted successfully");
        }
        return ResponseEntity.noContent().build();
    }
}
