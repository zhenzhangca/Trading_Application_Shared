package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Override
    Optional<Account> findById(Integer id);

    @Query(value = "SELECT * FROM ACCOUNT WHERE TRADER_ID = ?1", nativeQuery = true)
    Account findByTraderId(Integer traderId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ACCOUNT SET AMOUNT = ?2 WHERE ID = ?1", nativeQuery = true)
    void updateAmountById(Integer id, Double amount);
}
