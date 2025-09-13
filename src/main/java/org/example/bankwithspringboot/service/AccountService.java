package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.AccountRequest;
import org.example.bankwithspringboot.dto.request.Transaction;
import org.example.bankwithspringboot.dto.response.AccountResponse;
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

    public List<AccountResponse> getAllAccounts() {
        AccountResponse response = new AccountResponse();
        return accountRepository.findAll().stream()

                .map(account ->
                {
                    response.setAccountNumber(account.getAccountNumber());
                    response.setAccountType(account.getAccountType());
                    response.setBalance(account.getBalance());
                    return response;
                }).toList();
    }

    public Optional<AccountResponse> findAccountByAccountNumber(String accountNumber) {
        Optional<Account> found = accountRepository.findAccountByAccountNumber(accountNumber);
        return found.map(account -> new AccountResponse(account.getAccountNumber(), account.getBalance(), account.getAccountType()));
    }

    public AccountResponse depositMoney(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("deposit money must be positive");
        }

        Account account = accountRepository.findAccountByAccountNumber(transaction.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found for accountNumber: " + transaction.getAccountNumber()));

        account.setBalance(account.getBalance() - transaction.getAmount());
        Account saved =  accountRepository.save(account);
        return new AccountResponse(saved.getAccountNumber(), saved.getBalance(), saved.getAccountType());
    }

    public AccountResponse creditMoney(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("credit amount must be positive");
        }

        Account account = accountRepository.findAccountByAccountNumber(transaction.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found for accountNumber: " + transaction.getAccountNumber()));

        if (account.getBalance() < transaction.getAmount()) {
            throw new RuntimeException("Insufficient balance for accountNumber: " + transaction.getAccountNumber());
        }

        account.setBalance(account.getBalance() + transaction.getAmount());
        Account saved = accountRepository.save(account);

        return new AccountResponse(saved.getAccountNumber(), saved.getBalance(),saved.getAccountType());
    }

    @Transactional
    public Optional<AccountResponse> removeAccount(String accountNumber) {
        Optional<Account> removed = accountRepository.findAccountByAccountNumber(accountNumber).map(userFound -> {
            accountRepository.deleteByAccountNumber(accountNumber);
            return userFound;
        });
        return removed.map(account -> new AccountResponse(account.getAccountNumber(), account.getBalance(), account.getAccountType()));
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
