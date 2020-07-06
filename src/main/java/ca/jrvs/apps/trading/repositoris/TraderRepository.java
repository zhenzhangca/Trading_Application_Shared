package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {

    Trader save(Trader entity);

    Optional<Trader> findById(Integer id);

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
