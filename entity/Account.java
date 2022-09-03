package com.wei.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private String account;
    private String password;
    private String name;

    public Account(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
