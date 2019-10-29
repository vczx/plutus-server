package com.vince.plutus.dao;

import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;
import com.vince.plutus.repository.AccountRepository;
import com.vince.plutus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserAccountDaoImpl implements UserAccountDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<User> batchCreateUser(List<User> users) {
        Iterable<User> results = userRepository.saveAll(users);
        if (results != null) {
            List<User> savedUsers = new ArrayList<>();
            results.forEach(savedUsers::add);
            return savedUsers;
        }
        return null;

    }

    @Override
    public List<Account> getAccountByUser(Long userId) {
        List<Account> accounts = accountRepository.findAccountsByUserId(userId);
        return accounts;
    }

    @Override
    public List<User> getAllUsers() {
        Iterable<User> result = userRepository.findAll();
        List<User> users = new ArrayList<>();
        result.forEach(users::add);
        return users;
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean updateAccountBalance(Long accountId, Double newBalance) {
        Account account = accountRepository.findById(accountId).get();
        Objects.requireNonNull(account);
        account.setAccountBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        return updatedAccount.getAccountBalance() == newBalance;
    }

    @Override
    public Account getAccountById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElse(null);
    }
}
