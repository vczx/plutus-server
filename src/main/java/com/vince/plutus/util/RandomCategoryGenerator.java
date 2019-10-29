package com.vince.plutus.util;

import com.vince.plutus.model.TransactionCategory;
import com.vince.plutus.repository.TransactionCategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RandomCategoryGenerator {
    static Logger logger = LogManager.getLogger(RandomCategoryGenerator.class);

    @Autowired
    TransactionCategoryRepository repository;

    private static final List<TransactionCategory> CATEGORIES = new ArrayList<TransactionCategory>(Arrays.asList(
            new TransactionCategory("Shopping", "Shopping", "CR"),
            new TransactionCategory("Education", "Education", "CR"),
            new TransactionCategory("Food and Beverage", "Food and Beverage", "CR"),
            new TransactionCategory("Entertainment", "Entertainment", "CR"),
            new TransactionCategory("Outings", "Outings", "CR"),
            new TransactionCategory("Travel", "Travel", "CR"),
            new TransactionCategory("Medical", "Medical", "CR"),
            new TransactionCategory("Gadgets", "Gadgets", "CR"),
            new TransactionCategory("Games", "Games", "CR"),
            new TransactionCategory("Deposit", "Despoit", "DR"),
            new TransactionCategory("Salary", "Salary", "DR")
    ));

    public void insertCategories() {
        logger.info("Loading Transaction Categories");
        CATEGORIES.forEach(transactionCategory -> {
            logger.info("Loading Transaction Category : [ name: {}, Description: {} ]", transactionCategory.getCategory(), transactionCategory.getDescription());
        });
        repository.saveAll(CATEGORIES);
    }
}
