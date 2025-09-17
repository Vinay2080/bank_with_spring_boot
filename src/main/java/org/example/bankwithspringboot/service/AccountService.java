package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.accounts.AccountDeleteRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountTransaction;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.exception.ResourceAlreadyExistsException;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
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

    @Transactional
    public Account createAccount(AccountRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + request.getUserId()));

        String accountNumber = generateAccountNumber();

        if (accountRepository.findAccountByAccountNumber(accountNumber).isPresent()) {
            throw new ResourceAlreadyExistsException("Account is already present " + accountNumber);
        }
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : 0.0);
        account.setUser(user);
        account.setAccountType(request.getAccountType());
        return accountRepository.save(account);
    }

    public List<AccountResponse> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("no accounts found");
        }
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountResponse(
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getAccountType()))
                .toList();
    }

    public Optional<AccountResponse> findAccountByAccountNumber(String accountNumber) {
        Optional<Account> found = accountRepository.findAccountByAccountNumber(accountNumber);
        return found.map(account -> new AccountResponse(account.getAccountNumber(), account.getBalance(), account.getAccountType()));
    }

    @Transactional
    public AccountResponse depositMoney(AccountTransaction accountTransaction) {
        if (accountTransaction.getAmount() <= 0) {
            throw new IllegalArgumentException("deposit money must be positive");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountTransaction.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account not found for accountNumber: " + accountTransaction.getAccountNumber()));

        account.setBalance(account.getBalance() - accountTransaction.getAmount());
        Account saved = accountRepository.save(account);
        return new AccountResponse(saved.getAccountNumber(), saved.getBalance(), saved.getAccountType());
    }

    @Transactional
    public AccountResponse creditMoney(AccountTransaction accountTransaction) {
        if (accountTransaction.getAmount() <= 0) {
            throw new IllegalArgumentException("credit amount must be positive");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountTransaction.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account not found for accountNumber: " + accountTransaction.getAccountNumber()));

        if (account.getBalance() < accountTransaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance for accountNumber: " + accountTransaction.getAccountNumber());
        }

        account.setBalance(account.getBalance() + accountTransaction.getAmount());
        Account saved = accountRepository.save(account);

        return new AccountResponse(saved.getAccountNumber(), saved.getBalance(), saved.getAccountType());
    }

    @Transactional
    public boolean removeAccount(AccountDeleteRequest request) {
        if (findAccountByAccountNumber(request.getAccountNumber()).isEmpty()){
            throw new ResourceNotFoundException("Account with this this account number does not exists.\nEnter a valid account number");
        }
        accountRepository.deleteByAccountNumber(request.getAccountNumber());

        return true;
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
