package org.example.bankwithspringboot.service;

import jakarta.transaction.Transactional;
import org.example.bankwithspringboot.dto.request.accounts.AccountCreateRequest;
import org.example.bankwithspringboot.dto.request.accounts.AccountNumberRequest;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.enums.AccountStatus;
import org.example.bankwithspringboot.exception.ResourceAlreadyExistsException;
import org.example.bankwithspringboot.exception.ResourceNotFoundException;
import org.example.bankwithspringboot.mapper.AccountMapper;
import org.example.bankwithspringboot.model.Account;
import org.example.bankwithspringboot.model.User;
import org.example.bankwithspringboot.repository.AccountRepository;
import org.example.bankwithspringboot.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Enter a valid user ID"));
        String accountNumber = generateAccountNumber();

        if (accountRepository.findAccountByAccountNumber(accountNumber).isPresent()) {
            throw new ResourceAlreadyExistsException("Account with this account number already exits.");
        }
        Account account = accountMapper.dtoToCreateAccount(request);
        account.setAccountNumber(accountNumber);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addAccount(account);
        accountRepository.save(account);
        return accountMapper.entityToResponse(account);
    }

    public List<AccountResponse> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("no accounts found");
        }
        return accountMapper.entityToListOfAccountResponse(accounts);
    }

    public AccountResponse findAccountByAccountNumber(AccountNumberRequest request) {
        Account account = accountRepository.findAccountByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("No account exists with given account number. Enter a valid account number."));
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            throw new ResourceNotFoundException("This account is closed");
        }
        return accountMapper.entityToResponse(account);
    }

    @Transactional
    public boolean removeAccount(AccountNumberRequest request) {
        Account account = accountRepository.findAccountByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("No account exists with given account number. Enter a valid account number."));
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            return false;
        }
        account.setAccountStatus(AccountStatus.CLOSED);
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
