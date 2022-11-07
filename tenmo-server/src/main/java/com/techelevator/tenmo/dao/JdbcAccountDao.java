package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;



    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
       this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(int accountId) {
        String sql = "Select * from account join tenmo_user on tenmo_user.user_id = account.user_id where account_id = ? ";

       SqlRowSet set =  jdbcTemplate.queryForRowSet(sql,accountId);
       Account acc =new Account();
       if(set.next()){
           acc.setAccount_id(set.getInt("account_id"));
           acc.setUsername(set.getString("user_name")); acc .setBalance(set.getBigDecimal("balance"));
       }

       return acc;

    }
   /* @Override
    public BigDecimal getBalance(int accountId) {
        String sql = "Select balance from account where account_id = ? ";
        Account account =  jdbcTemplate.queryForObject(sql,Account.class,accountId);

        return account.getBalance();
    }*/

}
