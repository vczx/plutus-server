package com.vince.plutus.service;

import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;

import java.util.Date;
import java.util.List;

public interface AccountTransactionSerivce {
    Long addAccountTransaction(AccountTransactionDto accountTransactionDto);

    List<AccountTransactionDto> getAccountTransactionById(Long accountId);

    List<TransactionCategoryDto> getTransactionCategories();

    List<AccountTransactionDto> getTransactionByDate(Date date);

}
