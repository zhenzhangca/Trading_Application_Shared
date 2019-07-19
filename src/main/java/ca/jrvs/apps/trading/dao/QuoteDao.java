package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuoteDao extends JdbcCrudDao<Quote, String> {
    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdvcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTalbeName() {
        return null;
    }

    @Override
    public String getIdName() {
        return TABLE_NAME;
    }

    @Override
    Class getEntityClass() {
        return Quote.class;
    }

    @Override
    public Quote save(Quote quote){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = getSimpleJdbcInsert().execute(parameterSource);
        if(row!=1){
            throw new IncorrectResultSizeDataAccessException("Failed to insert ", 1, row);
        }
        return quote;

    }
    public void update(List<Quote> quoteList) {

    }

    public List<Quote> findAll() {
        String selectSql = "SELECT * FROM "+TABLE_NAME;
        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    }
}
