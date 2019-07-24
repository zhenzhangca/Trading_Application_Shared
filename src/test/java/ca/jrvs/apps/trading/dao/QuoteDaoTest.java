package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Test;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

public class QuoteDaoTest {
    DataSource dataSource = new AppConfig().dataSource();
    QuoteDao quoteDao = new QuoteDao(dataSource);

    @Test
    public void save() {
        Quote quote = new Quote();
        quote.setAskPrice(11.0);
        quote.setAskSize(100);
        quote.setBidPrice(10.5);
        quote.setBidSize(66);
        quote.setLastPrice(10.99);
        quote.setTicker("BBBB");
        Quote saveResult = quoteDao.save(quote);
        assertNotNull(saveResult);
    }

    @Test
    public void update() {
    }

    @Test
    public void findAll() {
        List<Quote> quotes = quoteDao.findAll();
        assertNotNull(quotes);
        System.out.println(quotes);
    }
}