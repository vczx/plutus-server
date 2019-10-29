package com.vince.plutus.dao;

import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.exception.NegativeBalanceException;
import com.vince.plutus.model.AccountTransaction;

import java.util.Date;
import java.util.List;

public interface TransactionDao {
    AccountTransaction addTransaction(AccountTransaction accountTransaction) throws NegativeBalanceException;

    List<AccountTransactionDto> getAccountTransactionById(Long accountId);

    List<TransactionCategoryDto> getAllTransactionCategory();

    List<AccountTransactionDto> getTransactionByDate(Date date);
}
