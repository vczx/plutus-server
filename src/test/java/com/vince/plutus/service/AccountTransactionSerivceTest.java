package com.vince.plutus.service;

import com.vince.plutus.dao.TransactionDaoImpl;
import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.exception.NegativeBalanceException;
import com.vince.plutus.model.AccountTransaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//TODO: change to mockitoRunner within spring contex
@SpringBootTest
class AccountTransactionSerivceTest {

    @Mock
    private TransactionDaoImpl transactionDao;

    @InjectMocks
    private AccountTransactionSerivceImpl accountTransactionSerivce;

    @Test
    void addAccountTransaction() throws NegativeBalanceException {
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId(1L);
        accountTransaction.setTransactionAmount(100.0);
        when(transactionDao.addTransaction(any(AccountTransaction.class))).thenReturn(accountTransaction);

        AccountTransactionDto dto = new AccountTransactionDto();
        TransactionCategoryDto transactionCategoryDto = new TransactionCategoryDto();
        transactionCategoryDto.setId(2L);
        transactionCategoryDto.setCategory("Dummy");
        transactionCategoryDto.setDescription("Dummy");
        dto.setCategory(transactionCategoryDto);
        dto.setDate(new Date());
        dto.setAmount(100.0);
        Long transactionId = accountTransactionSerivce.addAccountTransaction(dto);
        assertNotNull(transactionId);
        assertTrue(1L == transactionId);
    }

    @Test
    void negativeBalanceTest() throws NegativeBalanceException {
        when(transactionDao.addTransaction(any(AccountTransaction.class))).thenThrow(new NegativeBalanceException(""));

        AccountTransactionDto dto = new AccountTransactionDto();
        TransactionCategoryDto transactionCategoryDto = new TransactionCategoryDto();
        transactionCategoryDto.setId(2L);
        transactionCategoryDto.setCategory("Dummy");
        transactionCategoryDto.setDescription("Dummy");
        dto.setCategory(transactionCategoryDto);
        dto.setDate(new Date());
        dto.setAmount(100.0);

        Long transactionId = accountTransactionSerivce.addAccountTransaction(dto);
        assertNotNull(transactionId);
        assertEquals(Long.valueOf(-100), transactionId);
    }
}