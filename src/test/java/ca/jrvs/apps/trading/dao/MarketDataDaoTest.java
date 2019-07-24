package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Joiner;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Test;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MarketDataDaoTest {
    HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
    MarketDataConfig marketDataConfig;
    MarketDataDao marketDataDao;

    @Test
    public void findIexQuoteByTicker() {
        marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        try {
            //Test return value
            IexQuote aapl = marketDataDao.findIexQuoteByTicker("AMZN");
            Assert.assertNotNull(aapl);
            String quote = JsonUtil.toJson(aapl, true, false);
            System.out.println(quote);
            //Test httpResponse
            HttpGet httpGet = new HttpGet(String.format(marketDataDao.getBATCH_QUOTE_URL(), "AMZN"));
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
        marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken("pk_02baaf19c0e9412d937bbe5b063640f1");
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
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