package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpResponse;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Test;
import springfox.documentation.spring.web.json.Json;

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
        marketDataConfig.setToken("pk_02baaf19c0e9412d937bbe5b063640f1");
        marketDataDao = new MarketDataDao(httpClientConnectionManager, marketDataConfig);
        try {
            IexQuote aapl = marketDataDao.findIexQuoteByTicker("AMZN");
            Assert.assertNotNull(aapl);
            String quote = JsonUtil.toJson(aapl, true, false);
            System.out.println(quote);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        String readUri = "https://api.twitter.com/1.1/statuses/show.json?id=1147132559194824704";
//        try {
//            HttpResponse readResponse = httpHelper.httpGet(new URI(readUri));
//            System.out.println(EntityUtils.toString(readResponse.getEntity()));
//            assertNotNull(readResponse);
//            assertEquals(200, readResponse.getStatusLine().getStatusCode());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
            List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickerList);
            for (IexQuote iexQuote : iexQuotes) {
                String quote = JsonUtil.toJson(iexQuote, true, false);
                System.out.println(quote);
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}