package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.Test;

import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TraderDaoTest {
    DataSource dataSource = new AppConfig().dataSource();
    TraderDao traderDao = new TraderDao(dataSource);

    @Test
    public void save() {
        Trader trader = new Trader();
        trader.setCountry("Canada");
        trader.setDob(new Date());
        trader.setEmail("jrvs03@gmail.com");
        trader.setFirstName("jrvs03");
        trader.setLastName("jarvis03");
        //trader.setId(1);
        Trader saveResult = traderDao.save(trader);
        assertNotNull(saveResult);
    }

    @Test
    public void findById() {
        Trader findResult = traderDao.findById(4);
        assertNotNull(findResult);
        System.out.println(findResult);
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(2));
    }

    @Test
    public void deleteById() {
        traderDao.deleteById(4);
        assertFalse(traderDao.existsById(4));
    }
}