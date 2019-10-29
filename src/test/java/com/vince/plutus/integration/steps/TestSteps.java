package com.vince.plutus.integration.steps;

import com.vince.plutus.dto.AccountDto;
import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.model.Account;
import com.vince.plutus.model.User;
import com.vince.plutus.repository.AccountRepository;
import com.vince.plutus.service.AccountTransactionSerivce;
import com.vince.plutus.service.UserAccountService;
import com.vince.plutus.util.PlutusUtils;
import cucumber.api.java8.En;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class TestSteps implements En {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    AccountTransactionSerivce accountTransactionSerivce;

    @Autowired
    AccountRepository accountRepository;

    int orgSize = 0;
    Long accountId = null;
    private Long foundUserId;
    private Long foundAccountId;

    public TestSteps() {
        Given("user exists with details", (io.cucumber.datatable.DataTable dataTable) -> {
            List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
            String id = list.get(0).get("id");
            List<User> users = userAccountService.getAllUsers();
            Optional<User> userSearch = users.stream().filter(user1 -> user1.getId().equals(Long.valueOf(id))).findAny();
            assertTrue(userSearch.isPresent());

            User user = userSearch.get();
            foundUserId = userSearch.get().getId();
            assertTrue(user.getEmail().equals(list.get(0).get("email")));
            assertTrue(user.getUsername().equals(list.get(0).get("username")));

        });

        Given("user has the following accounts", (io.cucumber.datatable.DataTable dataTable) -> {
            List<AccountDto> accounts = userAccountService.getAccountByUser(foundUserId);
            if (accounts != null && accounts.size() > 0) {
                AccountDto accountDto = accounts.get(0);
                List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
                assertEquals(Long.valueOf(list.get(0).get("id")), accountDto.getId());
                assertEquals(list.get(0).get("accountNickName"), accountDto.getAccountNickName());
                assertEquals(list.get(0).get("accountType"), accountDto.getAccountType());
                assertEquals(Double.valueOf(list.get(0).get("accountBalance")), accountDto.getBalance());

                foundAccountId = accountDto.getId();
            }
        });

        When("user set the account balance to {double}", (Double accountBalance) -> {
            userAccountService.updateAccountBalance(foundAccountId, accountBalance);
        });

        Then("account balance should be {double}", (Double double1) -> {
            List<AccountDto> accountDtos = userAccountService.getAccountByUser(foundUserId);
            AccountDto accountDto = accountDtos.get(0);
            assertEquals(accountDto.getBalance(), double1);
        });

        //Transaction feature
        Given("User with a valid account exists with id {long}", (Long accountId) -> {
            List<AccountTransactionDto> trans = accountTransactionSerivce.getAccountTransactionById(accountId);
            orgSize = trans.size();
            this.accountId = accountId;

            assertNotNull(trans);
        });

        Given("user with starting balance as {double}", (Double startingBalance) -> {
            Optional<Account> result = accountRepository.findById(accountId);
            if (result.isPresent()) {
                Account account = result.get();
                assertEquals(startingBalance, account.getAccountBalance());
            }
        });

        When("user add a new transaction with the followings", (io.cucumber.datatable.DataTable dataTable) -> {
            List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
            AccountTransactionDto dto = new AccountTransactionDto();
            dto.setAccountId(Long.valueOf(list.get(0).get("accountId")));
            dto.setDate(PlutusUtils.parseDate(list.get(0).get("transactionDate")));
            dto.setAmount(Double.valueOf(list.get(0).get("transactionAmount")));
            dto.setComment(list.get(0).get("comment"));
            dto.setTitle(list.get(0).get("title"));
            dto.setCategory(new TransactionCategoryDto(Long.valueOf(list.get(0).get("categoryId")), null, null, list.get(0).get("type")));
            accountTransactionSerivce.addAccountTransaction(dto);
        });

        Then("number of transaction increased by {int} and use should see the following upon enquiry", (Integer int1, io.cucumber.datatable.DataTable dataTable) -> {

            List<AccountTransactionDto> trans = accountTransactionSerivce.getAccountTransactionById(accountId);
            Assert.assertTrue(trans.size() - orgSize == 1);
            List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
            AccountTransactionDto result = trans.get(trans.size() - 1);

            Assert.assertEquals(Long.valueOf(list.get(0).get("accountId")), result.getAccountId());
            assertTrue(Math.abs(Double.parseDouble(list.get(0).get("transactionAmount"))) == Math.abs(result.getAmount()));
            Assert.assertEquals(list.get(0).get("comment"), result.getComment());
            Assert.assertEquals(list.get(0).get("title"), result.getTitle());

        });

        Given("user have ending balance as {double}", (Double endingBalance) -> {
            Optional<Account> result = accountRepository.findById(accountId);
            if (result.isPresent()) {
                Account account = result.get();
                assertEquals(endingBalance, account.getAccountBalance());
            }
        });

    }
}
