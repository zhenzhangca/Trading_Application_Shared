package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class QuoteServiceIntTest {
    private AppConfig appConfig;
    private HttpClientConnectionManager httpClientConnectionManager;
    private MarketDataConfig marketDataConfig;
    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;
    private QuoteService quoteService;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        appConfig.setIex_host("https://cloud.iexapis.com/v1");
        httpClientConnectionManager = appConfig.httpClientConnectionManager();
        marketDataConfig = appConfig.marketDataConfig();
        quoteDao = new QuoteDao(appConfig.dataSource());
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        quoteService = new QuoteService(quoteDao, marketDataDao);
    }

    @Test
    public void initQuotes() {
        List<String> tickerList = new ArrayList<>();
        tickerList.add("GOOG");
        tickerList.add("TSLA");
        quoteService.initQuotes(tickerList);
        assertNotNull(quoteDao.findByTicker("TSLA"));
        assertNotNull(quoteDao.findByTicker("GOOG"));
    }

    @Test
    public void initQuote() {
        quoteService.initQuote("NFLX");
        assertNotNull(quoteDao.findByTicker("NFLX"));
    }

    @Test
    public void updateMarketData() {
        quoteService.updateMarketData();
    }
}