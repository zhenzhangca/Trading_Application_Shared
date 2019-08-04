package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class SecurityOrderDaoIntTest {
    private DataSource dataSource;
    private SecurityOrderDao securityOrderDao;

    @Before
    public void setup() {
        dataSource = new AppConfig().dataSource();
        securityOrderDao = new SecurityOrderDao(dataSource);
    }

    @Test
    public void save() {
        SecurityOrder securityOrder = new SecurityOrder();
        //explore more about SimpleJdbcInsert API, use SimpleJdbcInsert.usingGeneratedKeyColumns(String... columnNames) to specify the name sof any columns that have auto generated keys.
        //securityOrder.setId(2); //automatically generated
        securityOrder.setAccountId(2);
        securityOrder.setStatus(OrderStatus.FILLED);
        securityOrder.setTicker("cccc");
        securityOrder.setSize(10);
        SecurityOrder saveResult = securityOrderDao.save(securityOrder);
        assertNotNull(saveResult);
        System.out.println(saveResult);
    }

    @Test
    public void findById() {
        SecurityOrder result = securityOrderDao.findById(1);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void deleteById() {
        securityOrderDao.deleteById(1);
        assertFalse(securityOrderDao.existsById(1));
    }

    @Test
    public void existsById() {
        assertTrue(securityOrderDao.existsById(1));
    }
}