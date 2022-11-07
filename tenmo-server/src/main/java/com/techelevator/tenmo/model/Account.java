package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int account_id;
    private String user_name;
    private BigDecimal balance;

    public String getUser_name() {
        return user_name;
    }

    public void setUsername(String user_name) {
        this.user_name = user_name;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }



    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
