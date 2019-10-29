package com.vince.plutus.service;

import com.vince.plutus.dto.AccountDto;
import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;

import java.util.List;

public interface UserAccountService {
    List<User> createBatchUser(List<User> users);

    //Account- optimize here
    Account createAccount(Account account);

    List<User> getAllUsers();

    List<AccountDto> getAccountByUser(Long userId);

    boolean updateAccountBalance(Long accountId, Double newBalance);


}
