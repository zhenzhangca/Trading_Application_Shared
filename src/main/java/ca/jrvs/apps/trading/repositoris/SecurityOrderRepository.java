package ca.jrvs.apps.trading.repositoris;

import ca.jrvs.apps.trading.repositoris.models.domain.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityOrderRepository extends JpaRepository<SecurityOrder, Integer> {

    SecurityOrder save(SecurityOrder entity);

    Optional<SecurityOrder> findById(Integer id);

    @Query(value = "SELECT * FROM SECURITY_ORDER WHERE ACCOUNT_ID = ?1", nativeQuery = true)
    SecurityOrder findByAccountId(Integer accountId);

    @Query(value = "DELETE FROM SECURITY_ORDER WHERE ACCOUNT_ID = ?1", nativeQuery = true)
    void deleteByAccountId(Integer accountId);

    boolean existsById(Integer id);
}
