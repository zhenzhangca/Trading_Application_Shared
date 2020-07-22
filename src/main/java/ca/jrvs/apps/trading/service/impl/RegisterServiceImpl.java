package ca.jrvs.apps.trading.service.impl;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;
import ca.jrvs.apps.trading.repositoris.*;
import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import ca.jrvs.apps.trading.repositoris.models.domain.Position;
import ca.jrvs.apps.trading.repositoris.models.domain.Trader;
import ca.jrvs.apps.trading.repositoris.models.domain.TraderProfile;
import ca.jrvs.apps.trading.service.RegisterService;
import ca.jrvs.apps.trading.util.StringUtil;
import ca.jrvs.apps.trading.web.resources.TraderProfileResponse;
import ca.jrvs.apps.trading.web.resources.TraderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("RegisterServiceImpl")
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TraderRepository traderRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private PositionSqlRepository positionSqlRepo;
    @Autowired
    private SecurityOrderRepository securityOrderRepo;


    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param req trader info
     * @return traderAccountView
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public TraderProfileResponse createTraderAndAccount(TraderRequest req) {
        /**
         * Validate user input using reflection
         */
        if (StringUtil.isEmpty(req.getFirstName(), req.getLastName(), req.getCountry(), req.getEmail()) || req.getDob() == null) {
            throw new IllegalArgumentException("Fields of trader can not be empty or null");
        }
        Trader model = Trader.builder()
                .country(req.getCountry())
                .dob(req.getDob())
                .email(req.getEmail())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .build();
        //Create a new trader, then save into DB
        Trader newTrader = traderRepo.save(model);
        //Create a new account using traderId, then save into DB
        Account account = new Account();
        //account.setId(1); //auto-generated
        account.setTraderId(newTrader.getId());
        account.setAmount(0.0); //initialized with 0.0
        Account newAccount = accountRepo.save(account);
        //Create a new traderAccountView, then return
        TraderProfile traderProfile = TraderProfile.builder()
                .account(newAccount)
                .trader(newTrader)
                .build();

        return convertTraderProfile(traderProfile);
    }


    private TraderProfileResponse convertTraderProfile(TraderProfile model){
        return TraderProfileResponse.builder()
                .account(model.getAccount())
                .trader(model.getTrader())
                .build();
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        if (!traderRepo.existsById(traderId)) {
            throw new IllegalArgumentException("Trader with " + traderId + " doesn't exist!");
        }
        //Check account balance
        Account account = accountRepo.findByTraderId(traderId);
        if (account != null && account.getAmount() != 0) {
            throw new IllegalArgumentException("This account still has funds, cannot delete!");
        }
        //Check positions
        List<Position> positions = positionSqlRepo.findByAccountId(account.getId());
        if (positions.stream().anyMatch(position -> position.getPosition() != 0)) {
            throw new IllegalArgumentException("This account still has positions, cannot delete!");
        }
        securityOrderRepo.deleteByAccountId(account.getId());
        accountRepo.deleteById(account.getId());
        traderRepo.deleteById(traderId);
    }
}