package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class FundTransferService {

    private AccountDao accountDao;
    private TraderDao traderDao;

    @Autowired
    public FundTransferService(AccountDao accountDao, TraderDao traderDao) {
        this.accountDao = accountDao;
        this.traderDao = traderDao;
    }

    /**
     * Deposit a fund to the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader id
     * @param fund     found amount (can't be 0)
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public Account deposit(Integer traderId, Double fund) {
        validateInput(traderId, fund);
        Account account = accountDao.findByTraderId(traderId);
        Account updatedAccount = accountDao.updateAmountById(account.getId(), account.getAmount() + fund);
        return updatedAccount;
    }

    /**
     * Withdraw a fund from the account which is associated with the traderId
     * <p>
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader ID
     * @param fund     amount can't be 0
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public Account withdraw(Integer traderId, Double fund) {
        validateInput(traderId, fund);
        Account account = accountDao.findByTraderId(traderId);
        Account updatedAccount = null;
        if (account.getAmount() - fund >= 0) {
            updatedAccount = accountDao.updateAmountById(account.getId(), account.getAmount() - fund);
        } else {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        return updatedAccount;
    }

    /**
     * Helper method to validate user input
     */
    private void validateInput(Integer id, Double fund) {
        if (!traderDao.existsById(id)) {
            throw new IllegalArgumentException("Invalid trade ID!");
        }
        if (fund == null || fund <= 0) {
            throw new IllegalArgumentException("Positive amount is required here!");
        }
    }
}
