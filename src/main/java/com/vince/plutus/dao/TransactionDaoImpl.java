package com.vince.plutus.dao;

import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.exception.NegativeBalanceException;
import com.vince.plutus.model.Account;
import com.vince.plutus.model.AccountTransaction;
import com.vince.plutus.model.TransactionCategory;
import com.vince.plutus.repository.AccountRepository;
import com.vince.plutus.repository.AccountTransactionRepository;
import com.vince.plutus.repository.TransactionCategoryRepository;
import com.vince.plutus.util.PlutusUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    static Logger logger = LogManager.getLogger(TransactionDaoImpl.class);

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountTransaction addTransaction(AccountTransaction accountTransaction) throws NegativeBalanceException {
        Optional<Account> result = accountRepository.findById(accountTransaction.getAccount().getId());
        if (result.isPresent()) {
            Account account = result.get();
            if (accountTransaction.getTransactionCategory().getType().equals(PlutusUtils.TRANSACTION_TYPE_CR)) {
                accountTransaction.setTransactionAmount(-1 * accountTransaction.getTransactionAmount());
            }

            double newBalance = PlutusUtils.formatTwoDecimalPlaces(account.getAccountBalance() + accountTransaction.getTransactionAmount());
            if (newBalance <= 0) {
                //Not a valid transaction
                logger.error("Account Id {} attempting to have transaction resulting in negative account balance", account.getId());
                throw new NegativeBalanceException("Account Id " + account.getId() + " attempting to have transaction resulting in negative account balance");
            }
            account.setAccountBalance(newBalance);
            accountRepository.save(account);
            return accountTransactionRepository.save(accountTransaction);
        }
        return null;
    }

    @Override
    public List<AccountTransactionDto> getAccountTransactionById(Long accountId) {
        Iterable<AccountTransaction> result = accountTransactionRepository.getAccountTransactionByAccountId(accountId);
        Objects.requireNonNull(result);
        List<AccountTransaction> accountTransactions = PlutusUtils.fromIterableToList(result);
        return accountTransactions.stream().map(t -> t.fromModelToDto(t)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        List<TransactionCategory> results = PlutusUtils.fromIterableToList(transactionCategoryRepository.findAll());
        return results.stream().map(tc -> tc.fromModelToDto(tc)).collect(Collectors.toList());
    }

    @Override
    public List<AccountTransactionDto> getTransactionByDate(Date date) {
        List<AccountTransaction> transactions = accountTransactionRepository.getAccountTransactionByDateRange(date);
        return transactions.stream().map(t -> t.fromModelToDto(t)).collect(Collectors.toList());
    }
}
