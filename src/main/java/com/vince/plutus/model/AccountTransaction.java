package com.vince.plutus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vince.plutus.dto.AccountTransactionDto;
import com.vince.plutus.dto.TransactionCategoryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "accounttransaction")
@Getter
@Setter
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double transactionAmount;
    private Date transactionDate;
    @OneToOne
    private TransactionCategory transactionCategory = new TransactionCategory();
    private String title = "";
    private String comment = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account = new Account();

    public AccountTransaction() {
    }

    public static AccountTransaction fromDtoToModel(AccountTransactionDto dto) {
        AccountTransaction model = new AccountTransaction();
        model.setTransactionAmount(dto.getAmount());
        model.setTransactionDate(dto.getDate());
        model.account.setId(dto.getAccountId());
        model.setTransactionCategory(TransactionCategory.fromDtoToModel(dto.getCategory()));
        model.setComment(dto.getComment());
        model.setTitle(dto.getTitle());
        return model;
    }

    public AccountTransactionDto fromModelToDto(AccountTransaction model) {
        AccountTransactionDto dto = new AccountTransactionDto();
        dto.setAccountId(model.getAccount().getId());
        dto.setAmount(model.getTransactionAmount());
        dto.setDate(model.getTransactionDate());
        dto.setCategory(TransactionCategoryDto.fromModelToDto(model.getTransactionCategory()));
        dto.setTitle(model.getTitle());
        dto.setComment(model.getComment());
        return dto;
    }
}
