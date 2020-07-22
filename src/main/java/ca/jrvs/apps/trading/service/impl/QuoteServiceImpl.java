package ca.jrvs.apps.trading.service.impl;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;
import ca.jrvs.apps.trading.repositoris.MarketDataRepository;
import ca.jrvs.apps.trading.repositoris.QuoteRepository;
import ca.jrvs.apps.trading.repositoris.models.domain.IexQuote;
import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.web.resources.IexQuoteResponse;
import ca.jrvs.apps.trading.web.resources.QuoteRequest;
import ca.jrvs.apps.trading.web.resources.QuoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("QuoteServiceImpl")
@Slf4j
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    private QuoteRepository quoteRepo;
    @Autowired
    private MarketDataRepository marketDataRepo;

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Make sure set a default value for number field(s).
     */
    private static Quote convertQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = Quote.builder()
                .ticker(iexQuote.getSymbol())
                .lastPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getLatestPrice()).orElse("0")))
                .askPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getIexAskPrice()).orElse("0")))
                .bidPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getIexBidPrice()).orElse("0")))
                .bidSize(Integer.parseInt(Optional.ofNullable(iexQuote.getIexBidSize()).orElse("0")))
                .askSize(Integer.parseInt(Optional.ofNullable(iexQuote.getIexAskSize()).orElse("0")))
                .build();
        return quote;

    }

    /**
     * Add a list of new tickers to the quote table. Skip existing ticker(s).
     * - Get iexQuote(s)
     * - convert each iexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @throws ResourceNotFoundException                   if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public void initQuotes(List<String> tickers) throws Exception {
        //buildQuoteFromIexQuote helper method is used here //???findIexQuoteByTicker or findIedQuoteByTickers
        List<IexQuote> iexQuoteList = marketDataRepo.findIexQuoteByTickers(tickers);
        List<Quote> quotes = new ArrayList<>();
        iexQuoteList.stream().forEach(iexQuote -> {
            Quote quote = convertQuoteFromIexQuote(iexQuote);
            quotes.add(quote);
        });

        quotes.forEach(quote -> {
            if (!quoteRepo.existsById(quote.getTicker())) {
                quoteRepo.save(quote);
            }
        });
    }

    /**
     * Add a new ticker to the quote table. Skip existing ticker.
     *
     * @param ticker ticker/symbol
     * @throws ResourceNotFoundException                   if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public void initQuote(String ticker) throws Exception {
        initQuotes(Collections.singletonList(ticker));
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from the db
     * - foreach ticker get iexQuote
     * - convert iexQuote to quote entity
     * - persist quote to db
     *
     * @throws ResourceNotFoundException                   if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public List<QuoteResponse> updateMarketData() throws Exception {
        List<Quote> quotes = quoteRepo.findAll();
        List<String> tickers = quotes.stream().map(Quote::getTicker).collect(Collectors.toList());
        List<IexQuote> iexQuotes = marketDataRepo.findIexQuoteByTickers(tickers);
        List<Quote> updateQuotes = new ArrayList<>();
        iexQuotes.stream().forEach(iexQuote -> {
            Quote quote = convertQuoteFromIexQuote(iexQuote);
            updateQuotes.add(quote);
        });
        updateQuotes.stream().forEach(updateQuote -> {
            if (!quoteRepo.existsById(updateQuote.getTicker())) {
                throw new ResourceNotFoundException("Ticker not found:" + updateQuote.getTicker());
            }
            quoteRepo.save(updateQuote);
        });
        List<QuoteResponse> updateQuoteResponse = new ArrayList<>();
        updateQuotes.stream().forEach(updateQuote -> {
            QuoteResponse response = convertQuote(updateQuote);
            updateQuoteResponse.add(response);
        });
        return updateQuoteResponse;
    }

    private QuoteResponse convertQuote(Quote model) {
        return QuoteResponse.builder()
                .ticker(model.getTicker())
                .askPrice(model.getAskPrice())
                .askSize(model.getAskSize())
                .bidPrice(model.getBidPrice())
                .bidSize(model.getBidSize())
                .lastPrice(model.getLastPrice())
                .build();
    }

    public IexQuoteResponse findIexQuoteByTicker(String ticker) throws Exception {
        IexQuote iexQuote = marketDataRepo.findIexQuoteByTicker(ticker);
        return convertIexQuote(iexQuote);
    }

    private IexQuoteResponse convertIexQuote(IexQuote model) {
        return IexQuoteResponse.builder()
                .avgTotalVolume(model.getAvgTotalVolume())
                .calculationPrice(model.getCalculationPrice())
                .change(model.getChange())
                .changePercent(model.getChangePercent())
                .close(model.getClose())
                .closeTime(model.getCloseTime())
                .companyName(model.getCompanyName())
                .delayedPrice(model.getDelayedPrice())
                .delayedPriceTime(model.getDelayedPriceTime())
                .extendedChange(model.getExtendedChange())
                .extendedChangePercent(model.getExtendedChangePercent())
                .extendedPrice(model.getExtendedPrice())
                .extendedPriceTime(model.getExtendedPriceTime())
                .high(model.getHigh())
                .iexAskPrice(model.getIexAskPrice())
                .iexAskSize(model.getIexAskSize())
                .iexBidPrice(model.getIexBidPrice())
                .iexBidSize(model.getIexBidSize())
                .iexLastUpdated(model.getIexLastUpdated())
                .iexMarketPercent(model.getIexMarketPercent())
                .iexRealtimePrice(model.getIexRealtimePrice())
                .iexRealtimeSize(model.getIexRealtimeSize())
                .iexVolume(model.getIexVolume())
                .latestPrice(model.getLatestPrice())
                .latestSource(model.getLatestSource())
                .latestTime(model.getLatestTime())
                .latestUpdate(model.getLatestUpdate())
                .latestVolume(model.getLatestVolume())
                .low(model.getLow())
                .marketCap(model.getMarketCap())
                .open(model.getOpen())
                .openTime(model.getOpenTime())
                .peRatio(model.getPeRatio())
                .previousClose(model.getPreviousClose())
                .primaryExchange(model.getPrimaryExchange())
                .sector(model.getSector())
                .symbol(model.getSymbol())
                .week52High(model.getWeek52High())
                .week52Low(model.getWeek52Low())
                .ytdChange(model.getYtdChange())
                .build();
    }

    public List<QuoteResponse> getDailyList() {
        List<Quote> dailyList = quoteRepo.findAll();
        List<QuoteResponse> quoteResponseList = new ArrayList<>();
        dailyList.stream().forEach(quote -> {
            QuoteResponse quoteResponse = convertQuote(quote);
            quoteResponseList.add(quoteResponse);
        });
        return quoteResponseList;
    }

    public QuoteResponse update(QuoteRequest req) {
        if (!quoteRepo.existsById(req.getTicker())) {
            throw new ResourceNotFoundException("Ticker not found:" + req.getTicker());
        }
        Quote quote = Quote.builder()
                .ticker(req.getTicker())
                .askPrice(req.getAskPrice())
                .askSize(req.getAskSize())
                .bidPrice(req.getBidPrice())
                .bidSize(req.getBidSize())
                .lastPrice(req.getLastPrice())
                .build();
        Quote savedQuote = quoteRepo.save(quote);
        return convertQuote(savedQuote);
    }

}
