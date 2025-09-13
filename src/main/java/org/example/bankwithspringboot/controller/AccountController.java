package org.example.bankwithspringboot.controller;

import org.example.bankwithspringboot.dto.request.AccountRequest;
import org.example.bankwithspringboot.dto.response.AccountResponse;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber) {
        return accountService.findAccountByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<Account> depositMoney(@PathVariable String accountNumber, @RequestBody Account requestBody) {
        Account accountUpdated = accountService.depositMoney(requestBody.getBalance(), accountNumber);
        return ResponseEntity.ok(accountUpdated);
    }

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<Account> creditMoney(@PathVariable String accountNumber, @RequestBody Account account) {
        Account accountUpdated = accountService.creditMoney(accountNumber, account.getBalance());
        return ResponseEntity.ok(accountUpdated);
    }

    @DeleteMapping("/{accountNumber}/delete")
    public ResponseEntity<String> removeAccount(@PathVariable String accountNumber) {
        Optional<Account> accountRemoved = accountService.removeAccount(accountNumber);
        return accountRemoved
                .map(account -> ResponseEntity.ok("account removed successfully: " + account.getAccountNumber())).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
