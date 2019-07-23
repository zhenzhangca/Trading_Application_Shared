package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;


public class MarketDataDaoTest {
    HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
    MarketDataConfig marketDataConfig;

    MarketDataDao marketDataDao;

    @Test
    public void findIexQuoteByTicker() {
        marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://api.iextrading.com/1.0/tops");
        marketDataConfig.setToken("pk_02baaf19c0e9412d937bbe5b063640f1");
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        try {
            //IexQuote aapl = marketDataDao.findIexQuoteByTickers("AAPL");
            //String s = aapl.toString();
            //System.out.println(s);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void findIexQuoteByTickers() {
    }

}