package com.vince.plutus.controller;

import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import com.vince.plutus.service.AccountTransactionSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    @Autowired
    private AccountTransactionSerivce accountTransactionSerivce;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/getAllTransactionCategories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionCategoryDto> getAllTransactionCategories() {
        return accountTransactionSerivce.getTransactionCategories();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/getTransactionByMonth/{accountId}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountTransactionDto> getTransactionByMonth(@PathVariable Long accountId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return accountTransactionSerivce.getTransactionByDate(date);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/addNewTransaction", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long getAccountTransaction(@RequestBody AccountTransactionDto accountTransactionDto) {
        if (accountTransactionDto != null) {
            return accountTransactionSerivce.addAccountTransaction(accountTransactionDto);
        } else {
            return -1L;
        }
    }

    @GetMapping(value = "/getTransactionByAccount/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountTransactionDto> getTransactionByAccount(@PathVariable Long accountId) {
        if (accountId != null) {
            return accountTransactionSerivce.getAccountTransactionById(accountId);
        }
        return new ArrayList<>();
    }
}
