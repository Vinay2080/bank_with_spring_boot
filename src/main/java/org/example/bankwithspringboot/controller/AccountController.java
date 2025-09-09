package org.example.bankwithspringboot.controller;

import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/accounts")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account accountCreated = accountService.createAccount(account);
        return ResponseEntity.ok(accountCreated);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccountByUsername(@PathVariable String username) {
        return accountService.findAccountByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @PutMapping("/{username}/deposit")
    public ResponseEntity<Account> depositMoney(@PathVariable String username, @RequestBody Account requestBody) {
        Account accountUpdated = accountService.depositMoney(requestBody.getBalance(), username);
        return ResponseEntity.ok(accountUpdated);
    }

    @DeleteMapping("/{username}")
    public void removeAccount(@RequestBody Account account) {
        accountService.removeAccount(account.getUsername());
        ResponseEntity.ok("Account deleted successfully with username: " + account.getUsername());
    }
    @PutMapping("/{username}/credit")
    public ResponseEntity<Account> creditMoney(@PathVariable String username, @RequestBody Account account) {
        Account accountUpdated = accountService.creditMoney(username, account.getBalance());
        return ResponseEntity.ok(accountUpdated);
    }
}
