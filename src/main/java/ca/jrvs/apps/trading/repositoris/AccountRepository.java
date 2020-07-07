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

//    @Override
//    Optional<Account> findById(Integer id);
    @Query(value = "SELECT * FROM ACCOUNT WHERE TRADER_ID = ?1", nativeQuery = true)
    Account findByTraderId(Integer traderId);


    /**
     * @param id of account
     * @return updated account or null if id not found
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ACCOUNT SET AMOUNT = ?2 WHERE ID = ?1", nativeQuery = true)
    void updateAmountById(Integer id, Double amount);

//    Account updateAmountById(Integer id, Double amount) {
//        if (super.existsById(id)) {
//            String sql = "UPDATE " + TABLE_NAME + " SET amount=? WHERE id=?";
//            int row = jdbcTemplate.update(sql, amount, id);
//            logger.debug("Update amount row=" + row);
//            if (row != 1) {
//                throw new IncorrectResultSizeDataAccessException(1, row);
//            }
//            return findById(id);
//        }
//        return null;
//    }
}
