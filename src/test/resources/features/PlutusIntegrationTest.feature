Feature: User Interactino with account service

  #TODO: user unique identifier instead of number id
  Scenario: User input account balance
    Given user exists with details
      | id | email             | username |
      | 13 | Lucy@somemail.com | Lucy     |
    Given user has the following accounts
      | id | accountNickName | accountType | accountBalance |
      | 19 | Lucy_current    | current     | 0.0            |
    When user set the account balance to 100.23
    Then account balance should be 100.23

  Scenario: User add a DR type transaction
    Given User with a valid account exists with id 19
    Given user with starting balance as 100.23
    When user add a new transaction with the followings
      | title         | categoryId | type | comment               | transactionAmount | transactionDate | accountId |
      | Credit Salary | 11         | DR   | salary for this month | 2000              | 2019-9-28       | 19        |
    Then number of transaction increased by 1 and use should see the following upon enquiry
      | title         | categoryId | comment               | transactionAmount | transactionDate | accountId |
      | Credit Salary | 11         | salary for this month | 2000              | 2019-9-28       | 19        |
    Then user have ending balance as 2100.23

  Scenario: User add a CR Type transaction
    Given User with a valid account exists with id 19
    Given user with starting balance as 2100.23
    When user add a new transaction with the followings
      | title         | categoryId | type | comment       | transactionAmount | transactionDate | accountId |
      | Buy an iphone | 9          | CR   | new iphone 11 | 1399.8            | 2019-10-28      | 19        |
    Then number of transaction increased by 1 and use should see the following upon enquiry
      | title         | categoryId | comment       | transactionAmount | transactionDate | accountId |
      | Buy an iphone | 9          | new iphone 11 | 1399.8            | 2019-10-28      | 19        |
    Then user have ending balance as 700.43




