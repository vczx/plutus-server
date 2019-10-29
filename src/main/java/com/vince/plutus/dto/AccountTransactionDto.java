package com.vince.plutus.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountTransactionDto {

    private Double amount;
    private Date date;
    private TransactionCategoryDto category;
    private String comment;
    private String title;
    private Long accountId;

    public AccountTransactionDto() {
    }

    public AccountTransactionDto(Double amount, Date date, TransactionCategoryDto category, Long accountId) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.accountId = accountId;
    }
}
