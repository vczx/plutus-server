package com.vince.plutus.repository;

import com.vince.plutus.model.AccountTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
    Iterable<AccountTransaction> getAccountTransactionByAccountId(@Param("accountId") Long accountId);

    @Query("select at from AccountTransaction at where MONTH(at.transactionDate) = MONTH( :date) and YEAR(at.transactionDate) = YEAR(:date)")
    List<AccountTransaction> getAccountTransactionByDateRange(@Param("date") Date date);
}
