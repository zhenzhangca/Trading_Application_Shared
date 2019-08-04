package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MarketDataDaoIntTest {
    private AppConfig appConfig;
    private HttpClientConnectionManager httpClientConnectionManager;
    private MarketDataConfig marketDataConfig;
    private MarketDataDao marketDataDao;

    @Before
    public void setup() {
        appConfig = new AppConfig();
        appConfig.setIex_host("https://cloud.iexapis.com/v1");
        httpClientConnectionManager = appConfig.httpClientConnectionManager();
        marketDataConfig = appConfig.marketDataConfig();
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
    }

    @Test
    public void findIexQuoteByTicker() {
        try {
            //Test return value
            IexQuote aapl = marketDataDao.findIexQuoteByTicker("FB");
            Assert.assertNotNull(aapl);
            String quote = JsonUtil.toJson(aapl, true, false);
            System.out.println(quote);
            //Test httpResponse
            HttpGet httpGet = new HttpGet(String.format(marketDataDao.getBATCH_QUOTE_URL(), "FB"));
            CloseableHttpClient httpClient = marketDataDao.getHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Assert.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findIexQuoteByTickers() {
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AAPL");
        tickerList.add("KBH");
        tickerList.add("AMZN");

        try {
            //Test return value
            List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickerList);
            for (IexQuote iexQuote : iexQuotes) {
                String quote = JsonUtil.toJson(iexQuote, true, false);
                Assert.assertNotNull(quote);
                System.out.println(quote);
            }
            //Test httpResponse
            HttpGet httpGet = new HttpGet(String.format(marketDataDao.getBATCH_QUOTE_URL(), "AMZN,KBH,AAPL"));
            CloseableHttpClient httpClient = marketDataDao.getHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Assert.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}