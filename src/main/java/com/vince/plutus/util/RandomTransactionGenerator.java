package com.vince.plutus.util;

import com.vince.plutus.dto.AccountDto;
import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.model.User;
import com.vince.plutus.service.AccountTransactionSerivce;
import com.vince.plutus.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomTransactionGenerator {

    static Logger logger = LogManager.getLogger(RandomTransactionGenerator.class);
    @Autowired
    private AccountTransactionSerivce accountTransactionSerivce;

    @Autowired
    private UserAccountService userAccountService;

    private List<TransactionCategoryDto> categoryDtos;

    public void createRandomTransactionForUsers() {
        List<User> users = userAccountService.getAllUsers();
        User pickedUser = users.get(0);
        List<AccountDto> accounts = userAccountService.getAccountByUser(pickedUser.getId());

        categoryDtos = accountTransactionSerivce.getTransactionCategories();
        accounts.forEach(account -> {
            userAccountService.updateAccountBalance(account.getId(), 1000000.0);
            for (int i = 0; i < PlutusUtils.MOCK_TRANSACTION_AMOUNT; i++)
                createRandomTransactions(account.getId());
        });
        System.out.println("helllo");
    }

    private void createRandomTransactions(Long accountId) {
        Date d1 = PlutusUtils.parseDate("2018-01-1");
        Date d2 = PlutusUtils.parseDate("2019-10-27");

        Date randomDate = new Date(ThreadLocalRandom.current()
                .nextLong(d1.getTime(), d2.getTime()));
        AccountTransactionDto atDto = new AccountTransactionDto();
        atDto.setDate(randomDate);
        atDto.setCategory(categoryDtos.get(ThreadLocalRandom.current().nextInt(0, categoryDtos.size() - 1)));
        atDto.setAmount(PlutusUtils.formatTwoDecimalPlaces(ThreadLocalRandom.current().nextDouble(1, 1000)));
        atDto.setAccountId(accountId);
        atDto.setTitle("Related to " + atDto.getCategory().getCategory());
        atDto.setComment("Comment is " + atDto.getCategory().getCategory());
        logger.info("Generating Random Transaction for Account Id : {}, TransactionDate {}, TransactionAmount {}, TransactionCategory {} "
                , atDto.getAccountId(), atDto.getDate(), atDto.getAmount(), atDto.getCategory());
        accountTransactionSerivce.addAccountTransaction(atDto);

    }


}
