package com.vince.plutus.repository;

import com.vince.plutus.model.TransactionCategory;
import org.springframework.data.repository.CrudRepository;

public interface TransactionCategoryRepository extends CrudRepository<TransactionCategory, Long> {
}
