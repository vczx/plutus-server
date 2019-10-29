package com.vince.plutus.dao;

import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;

import java.util.List;

public interface UserAccountDao {
    List<User> batchCreateUser(List<User> user);

    List<Account> getAccountByUser(Long userId);

    List<User> getAllUsers();

    Account createAccount(Account account);

    boolean updateAccountBalance(Long accountId, Double newBalance);

    Account getAccountById(Long accountId);
}
