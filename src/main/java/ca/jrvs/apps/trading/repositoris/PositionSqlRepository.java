package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.Position;
import ca.jrvs.apps.trading.util.PositionRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class PositionSqlRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Position> findByAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String sql = String.join("",
                "select * ",
                "from POSITION ",
                "where account_id = :accountId");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountId", accountId);
        List<Position> results = namedParameterJdbcTemplate.query(sql, parameters, new PositionRowMapper());
        return results;
    }

    public Long findByIdAndTicker(Integer accountId, String ticker) {
        if (accountId == null || ticker == null) {
            throw new IllegalArgumentException("ID or ticker can't be null");
        }
        String sql = String.join("",
                "select * ",
                "from POSITION ",
                "where account_id = :accountId ",
                "and ticker = :ticker");
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountId", accountId);
        parameters.addValue("ticker", ticker);
        List<Position> results = namedParameterJdbcTemplate.query(sql, parameters, new PositionRowMapper());
        return results.get(0).getPosition();
    }
}