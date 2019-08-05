package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao extends JdbcCrudDao {
    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);
    private static final String TABLE_NAME = "position";
    private static final String ID_NAME = "account_id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_NAME);
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
    public Class getEntityClass() {
        return Position.class;
    }

    public List<Position> findByAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        List<Position> resultList = null;
        try {
            resultList
                    = jdbcTemplate.query("select * from " + TABLE_NAME + " where account_id = ?",
                    BeanPropertyRowMapper.newInstance(Position.class), accountId);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find account id:" + accountId, e);
        }
        if (resultList == null) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return resultList;
    }

    public Long findByIdAndTicker(Integer accountId, String ticker) {
        if (accountId == null || ticker == null) {
            throw new IllegalArgumentException("ID or ticker can't be null");
        }
        Position position = null;
        try {
            position
                    = jdbcTemplate.queryForObject("select * from " + TABLE_NAME + " where account_id = ? and ticker = ?",
                    BeanPropertyRowMapper.newInstance(Position.class), accountId, ticker);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find account ID:" + accountId, e + " or ticker:" + ticker, e);
        }
        if (position == null) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return position.getPosition();
    }
}
