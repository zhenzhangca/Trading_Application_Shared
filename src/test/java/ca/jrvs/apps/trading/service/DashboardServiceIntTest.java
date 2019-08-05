package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static ca.jrvs.apps.trading.util.JsonUtil.toJson;
import static org.junit.Assert.assertNotNull;

public class DashboardServiceIntTest {
    private AppConfig appConfig;
    private DataSource dataSource;
    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;
    private DashboardService dashboardService;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        dataSource = appConfig.dataSource();
        traderDao = new TraderDao(dataSource);
        positionDao = new PositionDao(dataSource);
        accountDao = new AccountDao(dataSource);
        quoteDao = new QuoteDao(dataSource);
        dashboardService = new DashboardService(traderDao, positionDao, accountDao, quoteDao);
    }

    @Test
    public void getTraderAccount() throws JsonProcessingException {
        TraderAccountView traderAccountView = dashboardService.getTraderAccount(1);
        assertNotNull(traderAccountView);
        System.out.println(toJson(traderAccountView, true, false));
    }

    @Test
    public void getProfileViewByTraderId() throws JsonProcessingException {
        PortfolioView profileView = dashboardService.getProfileViewByTraderId(1);
        assertNotNull(profileView);
        System.out.println(toJson(profileView, true, false));
    }
}