package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.AccountRequest;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.lang.StringBuilder;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(AccountRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for id: " + request.getUserId()));
//        account.setId(null);
        String accountNumber = generateAccountNumber();

        if (accountRepository.findAccountByAccountNumber(accountNumber).isPresent()) {
            throw new RuntimeException("Account is already present " + accountNumber);
        }
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : 0.0);
        account.setUser(user);
        account.setAccountType(request.getAccountType());
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
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

    @Transactional
    public Optional<Account> removeAccount(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber).map(userFound -> {
            accountRepository.deleteByAccountNumber(accountNumber);
            return userFound;
        });
    }

    // helper methods

    public String generateAccountNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("2025");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
