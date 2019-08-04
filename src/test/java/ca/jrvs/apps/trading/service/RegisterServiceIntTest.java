package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class RegisterServiceIntTest {
    private AppConfig appConfig;
    private DataSource dataSource;
    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;
    private RegisterService registerService;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        dataSource = appConfig.dataSource();
        traderDao = new TraderDao(dataSource);
        accountDao = new AccountDao(dataSource);
        positionDao = new PositionDao(dataSource);
        securityOrderDao = new SecurityOrderDao(dataSource);
        registerService = new RegisterService(traderDao, accountDao, positionDao, securityOrderDao);
    }

    @Test
    public void createTraderAndAccount() {
        Trader trader = new Trader();
//        trader.setId(5);
        trader.setLastName("zhang");
        trader.setFirstName("zhen");
        trader.setEmail("zz@jrvs.ca");
        trader.setCountry("Canada");
        trader.setDob(new Date());
        TraderAccountView traderAndAccount = registerService.createTraderAndAccount(trader);
        assertNotNull(traderAndAccount);
        System.out.println(traderAndAccount);
    }

    @Test
    public void deleteTraderById() {
        registerService.deleteTraderById(8);
    }
}