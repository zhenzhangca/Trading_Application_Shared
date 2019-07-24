package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.apache.http.conn.HttpClientConnectionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class QuoteServiceTest {
    private AppConfig appConfig;
    private HttpClientConnectionManager httpClientConnectionManager;
    private MarketDataConfig marketDataConfig;
    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;
    private QuoteService quoteService;

    @Test
    public void initQuotes() {
        appConfig = new AppConfig();
        appConfig.setIex_host("https://cloud.iexapis.com/v1");
        httpClientConnectionManager = appConfig.httpClientConnectionManager();
        marketDataConfig = appConfig.marketDataConfig();
        quoteDao = new QuoteDao(appConfig.dataSource());
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        quoteService = new QuoteService(quoteDao, marketDataDao);

        List<String> tickerList = new ArrayList<>();
        tickerList.add("AMZN");
        tickerList.add("FB");
        quoteService.initQuotes(tickerList);
        assertNotNull(quoteDao.findByTicker("AMZN"));
    }

    @Test
    public void initQuote() {
        appConfig = new AppConfig();
        appConfig.setIex_host("https://cloud.iexapis.com/v1");
        httpClientConnectionManager = appConfig.httpClientConnectionManager();
        marketDataConfig = appConfig.marketDataConfig();
        quoteDao = new QuoteDao(appConfig.dataSource());
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        quoteService = new QuoteService(quoteDao, marketDataDao);
        quoteService.initQuote("AAPL");
        assertNotNull(quoteDao.findByTicker("AAPL"));
    }

    @Test
    public void updateMarketData() {
        appConfig = new AppConfig();
        appConfig.setIex_host("https://cloud.iexapis.com/v1");
        httpClientConnectionManager = appConfig.httpClientConnectionManager();
        marketDataConfig = appConfig.marketDataConfig();
        quoteDao = new QuoteDao(appConfig.dataSource());
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        quoteService = new QuoteService(quoteDao, marketDataDao);
        quoteService.updateMarketData();
    }
}