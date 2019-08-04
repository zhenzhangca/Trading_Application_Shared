package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.Position;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PositionDaoIntTest {
    private DataSource dataSource;
    private PositionDao positionDao;


    @Before
    public void setup() {
        dataSource = new AppConfig().dataSource();
        positionDao = new PositionDao(dataSource);
    }

    @Test
    public void findByAccountId() {
        List<Position> result = positionDao.findByAccountId(2);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void findByIdAndTicker() {
        Long position = positionDao.findByIdAndTicker(2, "bbbb");
        System.out.println(position);
        assertEquals(new Long(10), position);
    }
}