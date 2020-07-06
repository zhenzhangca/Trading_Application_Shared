package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository  extends JpaRepository<Quote, String> {

    Optional<Quote> findById(String ticker);

//    @Override
//    public Quote save(Quote quote) {
//        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
//        int row = getSimpleJdbcInsert().execute(parameterSource);
//        if (row != 1) {
//            throw new IncorrectResultSizeDataAccessException("Failed to insert ", 1, row);
//        }
//        return quote;
//    }
//    /**
//     * https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc-batch-list
//     */
//    public void update(List<Quote> quotes) {
//        String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
//
//        //Prepare batch update values (order must match updateSql question mark)
//        List<Object[]> batch = new ArrayList<>();
//        quotes.forEach(quote -> {
//            if (!existsById(quote.getTicker())) {
//                throw new ResourceNotFoundException("Ticker not found:" + quote.getTicker());
//            }
//            Object[] values = new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
//                    quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
//            batch.add(values);
//        });
//
//        int[] rows = jdbcTemplate.batchUpdate(updateSql, batch);
//        int totalRow = Arrays.stream(rows).sum();
//        if (totalRow != quotes.size()) {
//            throw new IncorrectResultSizeDataAccessException("Number of rows ", quotes.size(), totalRow);
//        }
//    }
//
//    public List<Quote> findAll() {
//        String selectSql = "SELECT * FROM " + TABLE_NAME;
//        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
//    }
//
//    public Quote findByTicker(String ticker) {
//        if (ticker == null) {
//            throw new IllegalArgumentException("Ticker can't be null");
//        }
//        Quote quote = null;
//        try {
//            quote = jdbcTemplate
//                    .queryForObject("select * from " + TABLE_NAME + " where ticker = ?",
//                            BeanPropertyRowMapper.newInstance(Quote.class), ticker);
//        } catch (EmptyResultDataAccessException e) {
//            throw new IllegalArgumentException("Can't find quote with " + ticker, e);
//        }
//        return quote;
//    }
}
