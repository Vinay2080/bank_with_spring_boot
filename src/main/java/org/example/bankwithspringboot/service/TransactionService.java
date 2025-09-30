package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.transactions.TransactionRequest;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.model.Transaction;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.TransactionRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean transferMoney(TransactionRequest request) {
        Account accountDebit = accountRepository.findAccountByAccountNumber(request.getDebitAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("account number for debit account does not exists"));
        Account accountCredit = accountRepository.findAccountByAccountNumber(request.getCreditAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("account number for credit account does not exists"));

        if (!passwordEncoder.matches(request.getPassword(), accountDebit.getPassword())) {
            throw new BadCredentialsException("incorrect password");
        }

        if (request.getAmount() >= accountDebit.getBalance()){
            throw new IllegalArgumentException("insufficient balance");
        }
        accountDebit.setBalance(accountDebit.getBalance() - request.getAmount());
        accountRepository.save(accountDebit);

        accountCredit.setBalance(accountCredit.getBalance() + request.getAmount());
        accountRepository.save(accountCredit);

        Transaction transaction = new Transaction();

        transaction.setAccountCredited(request.getCreditAccountNumber());
        transaction.setAccountDebited(request.getDebitAccountNumber());
        transaction.setAccount(accountDebit);
        transactionRepository.save(transaction);
        return true;
    }
}
