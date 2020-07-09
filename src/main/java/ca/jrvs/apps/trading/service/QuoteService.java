package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import ca.jrvs.apps.trading.web.resources.IexQuoteResponse;
import ca.jrvs.apps.trading.web.resources.QuoteRequest;
import ca.jrvs.apps.trading.web.resources.QuoteResponse;

import java.util.List;

public interface QuoteService {

    List<QuoteResponse> updateMarketData() throws Exception;

    IexQuoteResponse findIexQuoteByTicker(String ticker) throws Exception;

    List<QuoteResponse> getDailyList();

    QuoteResponse update(QuoteRequest req);

    void initQuote(String ticker) throws Exception;

}

