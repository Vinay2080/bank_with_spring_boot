package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.accounts.AccountCreateRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountNumberRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountTransactionRequest;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.exception.ResourceAlreadyExistsException;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public AccountResponse createAccount(AccountCreateRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("Enter a valid user ID"));
        String accountNumber = generateAccountNumber();

        if (accountRepository.findAccountByAccountNumber(accountNumber).isPresent()) {
            throw new ResourceAlreadyExistsException("Account with this account number already exits.");
        }
        Account account = new Account();
        account.setAccountType(request.getAccountType());
        account.setAccountNumber(accountNumber);
        account.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : 0.0);
        account.setUser(user);

        Account saved = accountRepository.save(account);
        return new AccountResponse(
                saved.getAccountNumber(),
                saved.getBalance(),
                saved.getAccountType()
        );
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

public AccountResponse findAccountByAccountNumber(AccountNumberRequest request) {
    Account account = accountRepository.findAccountByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("No account exists with given account number. Enter a valid account number."));
    return new AccountResponse
            (account.getAccountNumber(),
                    account.getBalance(),
                    account.getAccountType());
}

@Transactional
public AccountResponse depositMoney(AccountTransactionRequest accountTransactionRequest) {
    if (accountTransactionRequest.getAmount() <= 0) {
        throw new IllegalArgumentException("deposit money must be positive");
    }

    Account account = accountRepository.findAccountByAccountNumber(accountTransactionRequest.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account not found for accountNumber: " + accountTransactionRequest.getAccountNumber()));

    account.setBalance(account.getBalance() - accountTransactionRequest.getAmount());
    Account saved = accountRepository.save(account);
    return new AccountResponse(saved.getAccountNumber(), saved.getBalance(), saved.getAccountType());
}

@Transactional
public AccountResponse creditMoney(AccountTransactionRequest accountTransactionRequest) {
    if (accountTransactionRequest.getAmount() <= 0) {
        throw new IllegalArgumentException("credit amount must be positive");
    }

    Account account = accountRepository.findAccountByAccountNumber(accountTransactionRequest.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account not found for accountNumber: " + accountTransactionRequest.getAccountNumber()));

    if (account.getBalance() < accountTransactionRequest.getAmount()) {
        throw new IllegalArgumentException("Insufficient balance for accountNumber: " + accountTransactionRequest.getAccountNumber());
    }

    account.setBalance(account.getBalance() + accountTransactionRequest.getAmount());
    Account saved = accountRepository.save(account);

    return new AccountResponse(saved.getAccountNumber(), saved.getBalance(), saved.getAccountType());
}

@Transactional
public boolean removeAccount(AccountNumberRequest request) {
    if (accountRepository.findAccountByAccountNumber(request.getAccountNumber()).isEmpty()) {
        throw new ResourceNotFoundException("Account with this this account number does not exists. Enter a valid account number");
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
