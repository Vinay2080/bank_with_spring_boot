package org.example.bankwithspringboot.controller;

import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.service.AccountService;
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

    @PostMapping("/create/{userId}")
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @PathVariable Long userId) {
        Account accountCreated = accountService.createAccount(account, userId);
        return ResponseEntity.ok(accountService.createAccount(account, userId));
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

    @DeleteMapping("/{accountNumber}/delete")
    public ResponseEntity<String> removeAccount(@PathVariable String accountNumber) {
        accountService.removeAccount(accountNumber);
        return ResponseEntity.ok("Account deleted successfully with accountNumber: " + accountNumber);
    }

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<Account> creditMoney(@PathVariable String accountNumber, @RequestBody Account account) {
        Account accountUpdated = accountService.creditMoney(accountNumber, account.getBalance());
        return ResponseEntity.ok(accountUpdated);
    }
}
