package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

public class AccountDaoIntTest {
    private DataSource dataSource;
    private AccountDao accountDao;

    @Before
    public void setup() {
        dataSource = new AppConfig().dataSource();
        accountDao = new AccountDao(dataSource);
    }

    @Test
    public void save() throws JsonProcessingException {
        Account account = new Account();
        account.setId(4);
        account.setTraderId(3);
        account.setAmount(8.99);
        Account saveResult = accountDao.save(account);
        assertNotNull(saveResult);
        System.out.println(JsonUtil.toJson(saveResult, true, false));
    }

    @Test
    public void findByTraderId() throws JsonProcessingException {
        Account findResult = accountDao.findByTraderId(1);
        assertNotNull(findResult);
        System.out.println(JsonUtil.toJson(findResult, true, false));
    }

    @Ignore
    public void findByTraderIdForUpdate() {

    }

    @Test
    public void updateAmountById() throws JsonProcessingException {
        //id of account
        Account updateResult = accountDao.updateAmountById(2, 7777.99);
        assertNotNull(updateResult);
        System.out.println(JsonUtil.toJson(updateResult, true, false));
    }
}