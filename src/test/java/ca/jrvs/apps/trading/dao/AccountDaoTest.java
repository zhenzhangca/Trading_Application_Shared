package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class AccountDaoTest {
    DataSource dataSource = new AppConfig().dataSource();
    AccountDao accountDao = new AccountDao(dataSource);

    @Test
    public void save() {
        Account account = new Account();
        account.setId(1);
        account.setTraderId(3);
        account.setAmount(11999.9);
        Account saveResult = accountDao.save(account);
        assertNotNull(saveResult);
        System.out.println(saveResult);
    }

    @Test
    public void findByTraderId() {
        Account findResult = accountDao.findByTraderId(3);
        assertNotNull(findResult);
        System.out.println(findResult);
    }

    @Test
    public void findByTraderIdForUpdate() {

    }

    @Test
    public void updateAmountById() {
        Account updateResult = accountDao.updateAmountById(2, 2999.99);
        assertNotNull(updateResult);
        System.out.println(updateResult);
    }
}