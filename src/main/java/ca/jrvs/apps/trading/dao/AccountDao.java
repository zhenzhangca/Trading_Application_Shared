package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    public Account findByIdForUpdate(Integer accountId){
        return null;
    }
    public void updateAmountById(Integer accountId, Double updateAmount){

    }
}
