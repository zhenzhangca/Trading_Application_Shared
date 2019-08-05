package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceUnitTest {

    @InjectMocks
    private QuoteService quoteService;
    @Mock
    private QuoteDao quoteDaoMock;
    @Mock
    private MarketDataDao markerDaoMock;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initQuotes() {
        List<String> tickerList = new ArrayList<>();
        tickerList.add("AMZN");
        tickerList.add("FB");

        when(markerDaoMock.findIexQuoteByTicker(anyString())).thenReturn(new IexQuote());
        when(quoteDaoMock.save(any(Quote.class))).thenReturn(new Quote());
        quoteService.initQuotes(tickerList);
        verify(markerDaoMock, times(2)).findIexQuoteByTicker(any(String.class));
        verify(quoteDaoMock, times(2)).save(any(Quote.class));
    }

    @Test
    public void initQuote() {
        String ticker = "AAPL";
        when(markerDaoMock.findIexQuoteByTicker(anyString())).thenReturn(new IexQuote());
        when(quoteDaoMock.save(any(Quote.class))).thenReturn(new Quote());
        quoteService.initQuote(ticker);
        verify(markerDaoMock, times(1)).findIexQuoteByTicker(any(String.class));
        verify(quoteDaoMock, times(1)).save(any(Quote.class));
    }

    @Test
    public void updateMarketData() {
        when(quoteDaoMock.findAll()).thenReturn(new ArrayList<>());
        when(markerDaoMock.findIexQuoteByTickers(any(List.class))).thenReturn(new ArrayList<IexQuote>());
        quoteService.updateMarketData();
        verify(markerDaoMock, times(1)).findIexQuoteByTickers(any(List.class));
        verify(quoteDaoMock, times(1)).findAll();
    }
}