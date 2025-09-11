package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account, Long userId) {
        if (accountRepository.findAccountByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new RuntimeException("Account is already present " + account.getAccountNumber());
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
        account.setId(null);

        account.setId(null);

        if (account.getBalance() == null) {
            account.setBalance(0.0);
        }

        account.setUser(user);
        return accountRepository.save(account);
    }

    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber);
    }

    public Account depositMoney(Double amount, String accountNumber) {
        if (amount <= 0) {
            throw new IllegalArgumentException("deposit money must be positive");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found for accountNumber: " + accountNumber));

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Transactional
    public void removeAccount(String accountNumber) {
        if (accountRepository.findAccountByAccountNumber(accountNumber).isEmpty()) {
            throw new RuntimeException("Account not found for accountNumber: " + accountNumber);
        }
        accountRepository.deleteByAccountNumber(accountNumber);
    }

    public Account creditMoney(String accountNumber, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("credit amount must be positive");
        }


        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found for accountNumber: " + accountNumber));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for accountNumber: " + accountNumber);
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

}
