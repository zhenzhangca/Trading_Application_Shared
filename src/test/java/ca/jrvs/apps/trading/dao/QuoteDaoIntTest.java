package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QuoteDaoIntTest {
    DataSource dataSource;
    QuoteDao quoteDao;

    @Before
    public void setup() {
        dataSource = new AppConfig().dataSource();
        quoteDao = new QuoteDao(dataSource);
    }

    @Test
    public void save() throws JsonProcessingException {
        Quote quote = new Quote();
        quote.setAskPrice(11.0);
        quote.setAskSize(100);
        quote.setBidPrice(10.5);
        quote.setBidSize(66);
        quote.setLastPrice(10.99);
        quote.setId("aaaa");
        Quote saveResult = quoteDao.save(quote);
        assertNotNull(saveResult);
        System.out.println(JsonUtil.toJson(saveResult, true, false));
    }

    @Test
    public void update() throws JsonProcessingException {
        List<Quote> quotes = new ArrayList<>();
        //Find exist quotes and update them
        Quote quote1 = quoteDao.findByTicker("aaaa");
        Quote quote2 = quoteDao.findByTicker("cccc");
        quote1.setAskSize(11111);
        quote1.setAskPrice(11.11);
        quote2.setAskSize(22222);
        quote2.setAskPrice(22.22);
        quotes.add(quote1);
        quotes.add(quote2);
        quoteDao.update(quotes);
        assertEquals(JsonUtil.toJson(quote1, true, false), JsonUtil.toJson(quoteDao.findByTicker("aaaa"), true, false));
        assertEquals(JsonUtil.toJson(quote2, true, false), JsonUtil.toJson(quoteDao.findByTicker("cccc"), true, false));
    }

    @Test
    public void findAll() {
        List<Quote> quotes = quoteDao.findAll();
        assertEquals(9, quotes.size());
        System.out.println(quotes);
    }
}
