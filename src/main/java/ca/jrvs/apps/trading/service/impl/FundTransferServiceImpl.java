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

@Service
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
        validateInput(traderId, fund);
        Account account = accountRepo.findByTraderId(traderId);
        Account updatedAccount = accountRepo.updateAmountById(account.getId(), account.getAmount() + fund);
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
        validateInput(traderId, fund);
        Account account = accountRepo.findByTraderId(traderId);
        Account updatedAccount = null;
        if (account.getAmount() - fund >= 0) {
            updatedAccount = accountRepo.updateAmountById(account.getId(), account.getAmount() - fund);
        } else {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        return convertAccount(updatedAccount);
    }

    /**
     * Helper method to validate user input
     */
    private void validateInput(Integer id, Double fund) {
        if (!traderRepo.existsById(id)) {
            throw new IllegalArgumentException("Invalid trader ID!");
        }
        if (fund == null || fund <= 0) {
            throw new IllegalArgumentException("Positive amount is required!");
        }
    }
}
