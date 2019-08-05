package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Date;

import static org.junit.Assert.*;

public class TraderDaoIntTest {
    private DataSource dataSource;
    private TraderDao traderDao;

    @Before
    public void setup() {
        dataSource = new AppConfig().dataSource();
        traderDao = new TraderDao(dataSource);
    }

    @Test
    public void save() {
        Trader trader = new Trader();
        trader.setCountry("Canada");
        trader.setDob(new Date());
        trader.setEmail("jrvs04@gmail.com");
        trader.setFirstName("jrvs04");
        trader.setLastName("jarvis04");
        //trader.setId(1);
        Trader saveResult = traderDao.save(trader);
        assertNotNull(saveResult);
    }

    @Test
    public void findById() {
        Trader trader = traderDao.findById(1);
        assertNotNull(trader);
        System.out.println(trader);
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(1));
    }

    @Test
    public void deleteById() {
        traderDao.deleteById(2);
        assertFalse(traderDao.existsById(2));
    }
}