package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.google.common.base.Joiner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketDataDao {
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private final String BATCH_QUOTE_URL;
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        BATCH_QUOTE_URL = marketDataConfig.getHost() + "/stock/market/batch?symbols=%s&types=quote&token="
                + marketDataConfig.getToken();
    }

    /**
     * https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,amzn&&types=quote&token=pk_02baaf19c0e9412d937bbe5b063640f1
     * @param tickerList
     * @return
     * @throws org.springframework.dao.DataRetrievalFailureException if unable to get HTTP response
     */
    public List<IexQuote> findIexQuoteByTickers(List<String> tickerList) {
        //Convert List into comma separated String
        String tickers = Joiner.on(",").join(tickerList);
        //Construct uri
        String uri = String.format(BATCH_QUOTE_URL, tickers);
        //System.out.println(uri);
        logger.info("GET URI: " + uri);
        //Get HTTP response body in String
        String httpResponse = executeHttpGet(uri);
        JSONObject iexQuoteJson = new JSONObject(httpResponse);
        if (iexQuoteJson.length() == 0) {
            throw new ResourceNotFoundException("Not found");
        }
        if (iexQuoteJson.length() != tickerList.size()) {
            throw new IllegalArgumentException("Invalid ticker or symbol");
        }
        //Unmarshal Json object
        List<IexQuote> iexQuotes = new ArrayList<>();
        iexQuoteJson.keys().forEachRemaining(ticker -> {
            try {
                String quoteStr = ((JSONObject) iexQuoteJson.get(ticker)).get("quote").toString();
                IexQuote iexQuote = JsonUtil.toObjectFromJson(quoteStr, IexQuote.class);
                iexQuotes.add(iexQuote);
            } catch (IOException e) {
                throw new DataRetrievalFailureException("Unable parse httpResponse: " + iexQuoteJson.get(ticker));
            }
        });
        return iexQuotes;

    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        List<IexQuote> quotes = findIexQuoteByTickers(Arrays.asList(ticker));
        if (quotes == null || quotes.size() != 1) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return quotes.get(0);
    }

    protected String executeHttpGet(String url) {
        try (CloseableHttpClient httpClient = getHttpClient()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                switch (httpResponse.getStatusLine().getStatusCode()) {
                    case 200:
                        //EntityUtils.toString() will also close inputStream in Entity
                        String responseBody = EntityUtils.toString(httpResponse.getEntity());
                        return Optional.ofNullable(responseBody).orElseThrow(() -> new IOException("Unexpected empty httpResponse body"));
                    case 404:
                        throw new ResourceNotFoundException("Not found");
                    default:
                        throw new DataRetrievalFailureException("Unexpected status: " + httpResponse.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException | ResourceNotFoundException e) {
            throw new DataRetrievalFailureException("Unable Http execution error", e);
        }
    }

    protected CloseableHttpClient getHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(httpClientConnectionManager);
        //Prevent connectionManager shutdown when calling httpClient.close()
        httpClientBuilder.setConnectionManagerShared(true);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }

    public String getBATCH_QUOTE_URL() {
        return BATCH_QUOTE_URL;
    }
}
