package ca.jrvs.apps.trading.service.impl;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;
import ca.jrvs.apps.trading.repositoris.AccountRepository;
import ca.jrvs.apps.trading.repositoris.TraderRepository;
import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.web.resources.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FundTransferServiceImpl")
@Slf4j
public class FundTransferServiceImpl implements FundTransferService {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private TraderRepository traderRepo;

    /**
     * Deposit a fund to the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader id
     * @param fund     found amount (can't be 0)
     * @return updated Account object
     * @throws ResourceNotFoundException                   if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public AccountResponse deposit(Integer traderId, Double fund) {
        //check whether trader id exists or not
        validateInput(traderId, fund);
        Account account = accountRepo.findByTraderId(traderId);
        accountRepo.updateAmountById(account.getId(), account.getAmount() + fund);
        Account updatedAccount = accountRepo.findByTraderId(traderId);
        return convertAccount(updatedAccount);
    }

    private AccountResponse convertAccount(Account model) {
        return AccountResponse.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .traderId(model.getTraderId())
                .build();
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
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public AccountResponse withdraw(Integer traderId, Double fund) {
        //check whether trader id exists or not
        validateInput(traderId, fund);
        Account account = accountRepo.findByTraderId(traderId);
        if (account.getAmount() - fund >= 0) {
            accountRepo.updateAmountById(account.getId(), account.getAmount() - fund);
        } else {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        Account updatedAccount = accountRepo.findByTraderId(traderId);
        return convertAccount(updatedAccount);
    }

    /**
     * Helper method to validate user input
     */
    private void validateInput(Integer id, Double fund) {
        if (!traderRepo.existsById(id)) {
            throw new IllegalArgumentException("Trader ID doesn't exist!");
        }
        if (fund == null || fund <= 0) {
            throw new IllegalArgumentException("Positive amount is required!");
        }
    }
}
