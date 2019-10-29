package com.vince.plutus.config;

import com.vince.plutus.util.RandomAccountGenerator;
import com.vince.plutus.util.RandomCategoryGenerator;
import com.vince.plutus.util.RandomTransactionGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class InitialDataLoad {

    static Logger logger = LogManager.getLogger(InitialDataLoad.class);

    @Autowired
    private RandomAccountGenerator randomAccountGenerator;

    @Autowired
    private RandomTransactionGenerator randomTransactionGenerator;

    @Autowired
    private RandomCategoryGenerator randomCategoryGenerator;

    @EventListener(ApplicationReadyEvent.class)
    public void initDataLoad() {
        generateRandomUserAndAccounts();
    }

    void generateRandomUserAndAccounts() {
        logger.info("Start loading data into database.....");

        randomCategoryGenerator.insertCategories();
        randomAccountGenerator.generateRandomUserAndAccounts(6, 1);
        randomTransactionGenerator.createRandomTransactionForUsers();

    }
}
