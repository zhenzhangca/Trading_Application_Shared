package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.repositoris.models.domain.Position;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionRowMapper implements RowMapper<Position> {

    @Override
    public Position mapRow(ResultSet rs, int rowNum) throws SQLException {

        Position position = Position.builder()
                .accountId(rs.getInt("ACCOUNT_ID"))
                .position(rs.getLong("POSITION"))
                .ticker(rs.getString("TICKER"))
                .build();

        return position;

    }
}