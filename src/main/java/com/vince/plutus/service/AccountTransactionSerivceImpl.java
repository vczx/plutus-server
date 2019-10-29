package com.vince.plutus.service;

import com.vince.plutus.dao.TransactionDao;
import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.exception.NegativeBalanceException;
import com.vince.plutus.model.AccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Service layer will be aware of dto from the UI and do transformation if required
 */
@Service
public class AccountTransactionSerivceImpl implements AccountTransactionSerivce {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional
    public Long addAccountTransaction(AccountTransactionDto accountTransactionDto) {
        AccountTransaction accountTransaction = AccountTransaction.fromDtoToModel(accountTransactionDto);
        AccountTransaction result = null;
        try {
            result = transactionDao.addTransaction(accountTransaction);
            return result.getId();
        } catch (NegativeBalanceException e) {
            //TODO: should provide a error code to upstream and wrapped inside error resposne entity
            return (long) NegativeBalanceException.CODE;
        }
    }

    @Override
    public List<AccountTransactionDto> getAccountTransactionById(Long accountId) {
        return transactionDao.getAccountTransactionById(accountId);
    }

    @Override
    public List<TransactionCategoryDto> getTransactionCategories() {
        return transactionDao.getAllTransactionCategory();
    }

    @Override
    public List<AccountTransactionDto> getTransactionByDate(Date date) {
        return transactionDao.getTransactionByDate(date);
    }
}
