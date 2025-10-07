package org.example.bankwithspringboot.mapper;

import org.example.bankwithspringboot.dto.request.accounts.AccountCreateRequest;
import org.example.bankwithspringboot.dto.response.accounts.AccountResponse;
import org.example.bankwithspringboot.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {


    @Mapping(target = "balance", source = "initialBalance")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    Account dtoToCreateAccount(AccountCreateRequest request);

    // accounts response
    List<AccountResponse> entityToListOfAccountResponse(List<Account> accounts);

    // account response
    AccountResponse entityToResponse(Account account);
}
