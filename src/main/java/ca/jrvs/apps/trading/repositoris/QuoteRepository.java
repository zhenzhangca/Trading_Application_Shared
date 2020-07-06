package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, String> {
    @Override
    Optional<Quote> findById(String ticker);

    @Override
    List<Quote> findAll();

    Quote save(Quote quote);

    @Override
    boolean existsById(String s);

}
