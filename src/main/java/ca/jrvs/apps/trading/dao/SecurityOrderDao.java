package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
    private static final String TABLE_NAME = "security_order";
    private static final String ID_NAME = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    Class getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    public SecurityOrder save(SecurityOrder entity) {
        return super.save(entity);
    }

    @Override
    public SecurityOrder findById(Integer id) {
        return super.findById(getIdName(), id, false, getEntityClass());
    }

    public SecurityOrder findByAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null!");
        }
        return super.findById("account_id", accountId, false, SecurityOrder.class);
    }

    public void deleteByAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null!");
        }
        super.deleteById("account_id", accountId);
    }

    @Override
    public boolean existsById(Integer id) {
        return super.existsById(id);
    }
}
