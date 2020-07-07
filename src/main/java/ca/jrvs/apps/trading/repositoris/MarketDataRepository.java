package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;
import ca.jrvs.apps.trading.repositoris.models.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MarketDataRepository {


    private final String BATCH_QUOTE_URL;
    @Autowired
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataRepository(@Value("${app.host}") String host, @Value("${app.token}") String token) {
        BATCH_QUOTE_URL = host + "/stock/market/batch?symbols=%s&types=quote&token="
                + token;
    }

    /**
     * https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,fb,tsla&types=quote&token=pk_02baaf19c0e9412d937bbe5b063640f1     * @param tickerList
     *
     * @return
     * @throws org.springframework.dao.DataRetrievalFailureException if unable to get HTTP response
     */
    public List<IexQuote> findIexQuoteByTickers(List<String> tickerList) throws Exception {
        //Convert List into comma separated String
        String tickers = Joiner.on(",").join(tickerList);
        //Construct uri
        String uri = String.format(BATCH_QUOTE_URL, tickers);
        //System.out.println(uri);
        log.info("GET URI: " + uri);
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
                String quoteStr = ((JSONObject) iexQuoteJson.get(ticker.toString())).get("quote").toString();
                IexQuote iexQuote = JsonUtil.toObjectFromJson(quoteStr, IexQuote.class);
                iexQuotes.add(iexQuote);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return iexQuotes;
    }

    public IexQuote findIexQuoteByTicker(String ticker) throws Exception {
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
