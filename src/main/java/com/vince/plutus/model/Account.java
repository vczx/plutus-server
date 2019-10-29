package com.vince.plutus.model;

import com.vince.plutus.dto.AccountDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
    private List<AccountTransaction> accountTransactions = new ArrayList<>();
    private String accountNickName;
    private String accountType;
    private Double accountBalance = 0.0;
    //prevent concurrent update
    @Version
    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(String accountNickName, String accountType) {
        this.accountNickName = accountNickName;
        this.accountType = accountType;
    }

    public AccountDto fromModelToDto(Account model) {
        Objects.requireNonNull(model);
        return new AccountDto(model.getId(), model.getAccountNickName(), model.getAccountType(), model.getAccountBalance());
    }
}
