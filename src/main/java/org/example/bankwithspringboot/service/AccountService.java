package org.example.bankwithspringboot.service;

import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()) {
            throw new RuntimeException("Account is already present " + account.getUsername());
        }

        account.setId(null);
        accountRepository.save(account);

        if (account.getBalance() == null) {
            account.setBalance(0.0);
        }
        return accountRepository.save(account);
    }

    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public Account depositMoney(Double amount, String username) {
        if (amount <= 0) {
            throw new IllegalArgumentException("deposit money must be positive");
        }

        Account account = accountRepository.findAccountByUsername(username).orElseThrow(() -> new RuntimeException("Account not found for username: " + username));

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public void removeAccount(String username) {
        if (accountRepository.findAccountByUsername(username).isEmpty()) {
            throw new RuntimeException("Account not found for username: " + username);
        }
        accountRepository.deleteByUsername(username);
    }

    public Account creditMoney(String username, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("deposit money must be positive");
        }

        Account account = accountRepository.findAccountByUsername(username).orElseThrow(()->new RuntimeException("Account not found for username: " + username));

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

}
