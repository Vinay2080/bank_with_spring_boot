package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.transactions.TransactionRequest;
import org.example.bankwithspringboot.dto.response.transaction.TransactionResponse;
import org.example.bankwithspringboot.enums.TransactionType;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.model.Transaction;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponse depositMoney(TransactionRequest request) {

        Account account = accountRepository.findAccountByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("account with " + request.getAccountNumber() + " account number does not exists enter a valid number"));

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }

        account.setBalance(account.getBalance() + request.getAmount());
        accountRepository.save(account);
        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAccountDebited(request.getAccountNumber());
        transaction.setBalance(account.getBalance());
        transaction.setAccountCredited(null);

        Transaction saved = transactionRepository.save(transaction);
        return new TransactionResponse(
                saved.getAccountDebited(),
                saved.getBalance()

        );
    }
}
