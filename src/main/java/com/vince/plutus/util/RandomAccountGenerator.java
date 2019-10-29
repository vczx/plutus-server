package com.vince.plutus.util;

import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;
import com.vince.plutus.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class RandomAccountGenerator {

    static Logger logger = LogManager.getLogger(RandomAccountGenerator.class);

    @Autowired
    private UserAccountService userAccountService;

    private static final List<String> dummyUserNames = new ArrayList<>(Arrays.asList("Jack", "Lucy", "Mario", "Zelda"));
    private static final String USER_EMAIL_POSTFIX = "@somemail.com";

    private static final String ACCOUNT_TYPE = "current";

    public boolean generateRandomUserAndAccounts(int noOfUser, int accountPerUser) {
        String userName = "";
        String userEmail = "";
        List<User> users = new ArrayList<>();
        for (int i = 0; i < noOfUser; i++) {
            int index = i % dummyUserNames.size();
            userName = dummyUserNames.get(index);
            if (i >= dummyUserNames.size()) {
                userName = userName + "_" + generateRandomPostfix();
            }
            userEmail = userName + USER_EMAIL_POSTFIX;
            users.add(new User(userName, userEmail));
        }

        userAccountService.createBatchUser(users);
        //Test update with save one by one
        users.forEach(user -> {
            createAccountForUser(user, 1);
            logger.info("Generating Random User -> user name : {}, user email {}", user.getUsername(), user.getEmail());
        });

        return true;
    }

    private boolean createAccountForUser(User user, int noOfAccounts) {
        Account account = null;
        if (user != null && user.getId() != null) {
            account = new Account(user.getUsername() + "_" + ACCOUNT_TYPE, ACCOUNT_TYPE);
            account.setUser(user);
        }
        userAccountService.createAccount(account);
        return true;
    }

    private String generateRandomPostfix() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
