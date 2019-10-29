package com.vince.plutus.repository;

import com.vince.plutus.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT a from Account a WHERE a.user.id = :userId")
    List<Account> findAccountsByUserId(@Param("userId") Long userId);
}
