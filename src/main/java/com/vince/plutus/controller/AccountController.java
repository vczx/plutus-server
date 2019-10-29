package com.vince.plutus.controller;

import com.vince.plutus.dto.AccountDto;
import com.vince.plutus.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Wrap response with ResponseEntity
@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private UserAccountService userAccountService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/getAccountsByUser/{userId}")
    public List<AccountDto> getAccountsByUser(@PathVariable Long userId) {
        if (userId != null) {
            return userAccountService.getAccountByUser(userId);
        } else {
            //Return error response here
        }
        return null;
    }

    @PostMapping(value = "/updateAccountBalance")
    public boolean updateAccountBalance(@RequestParam Long accountId, @RequestParam Double newBalance) {
        if (newBalance < 0) {
            return false;
        }
        return userAccountService.updateAccountBalance(accountId, newBalance);
    }
}
