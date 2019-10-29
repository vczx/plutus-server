package com.vince.plutus.service;

import com.vince.plutus.dao.TransactionDao;
import com.vince.plutus.dao.UserAccountDao;
import com.vince.plutus.dto.AccountDto;
import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    static Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    TransactionDao transactionDao;

    /**
     * Method exposed for admin to batch create users
     *
     * @param users
     * @return
     */
    @Override
    public List<User> createBatchUser(List<User> users) {
        return userAccountDao.batchCreateUser(users);
    }

    @Override
    public Account createAccount(Account account) {
        return userAccountDao.createAccount(account);
    }

    @Override
    public List<User> getAllUsers() {
        return userAccountDao.getAllUsers();
    }

    @Override
    public List<AccountDto> getAccountByUser(Long userId) {
        List<Account> accounts = userAccountDao.getAccountByUser(userId);
        List<AccountDto> dtos = accounts.stream().map(account -> account.fromModelToDto(account)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional
    public boolean updateAccountBalance(Long accountId, Double newBalance) {
        return userAccountDao.updateAccountBalance(accountId, newBalance);
    }


}
