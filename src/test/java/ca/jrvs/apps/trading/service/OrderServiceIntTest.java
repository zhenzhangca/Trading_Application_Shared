package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

public class OrderServiceIntTest {
    private AppConfig appConfig;
    private DataSource dataSource;
    private AccountDao accountDao;
    private QuoteDao quoteDao;
    private SecurityOrderDao securityOrderDao;
    private PositionDao positionDao;
    private OrderService orderService;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        dataSource = appConfig.dataSource();
        accountDao = new AccountDao(dataSource);
        quoteDao = new QuoteDao(dataSource);
        securityOrderDao = new SecurityOrderDao(dataSource);
        positionDao = new PositionDao(dataSource);
        orderService = new OrderService(accountDao, securityOrderDao, quoteDao, positionDao);
    }

    @Test
    public void executeMarketOrder() {
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setTicker("GOOG");
        marketOrderDto.setAccountId(1);
        marketOrderDto.setSize(199);
        SecurityOrder result = orderService.executeMarketOrder(marketOrderDto);
        assertNotNull(result);
        System.out.println(result);
    }
}