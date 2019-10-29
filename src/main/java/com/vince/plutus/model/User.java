package com.vince.plutus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    public User() {
    }

    public User(String userName, String userEmail) {
        this.username = userName;
        this.email = userEmail;
    }
}
