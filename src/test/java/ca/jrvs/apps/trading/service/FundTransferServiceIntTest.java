package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

public class FundTransferServiceIntTest {
    private AppConfig appConfig;
    private DataSource dataSource;
    private AccountDao accountDao;
    private TraderDao traderDao;
    private FundTransferService fundTransferService;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        dataSource = appConfig.dataSource();
        accountDao = new AccountDao(dataSource);
        traderDao = new TraderDao(dataSource);
        fundTransferService = new FundTransferService(accountDao, traderDao);
    }

    @Test
    public void deposit() {
        Account account = fundTransferService.deposit(1, 1500.0);
        assertNotNull(account);
    }

    @Test
    public void withdraw() {
        Account account = fundTransferService.withdraw(1, 1500.0);
        assertNotNull(account);
    }
}