package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Transactional
@Service
public class RegisterService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public RegisterService(TraderDao traderDao, AccountDao accountDao,
                           PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param trader trader info
     * @return traderAccountView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        /**
         * Validate user input using reflection
         */
        if (trader.getId() != null) {
            throw new IllegalArgumentException("ID is not allowed as it is auto-gen");
        }
        if (StringUtil.isEmpty(trader.getFirstName(), trader.getLastName(), trader.getCountry(), trader.getEmail()) || trader.getDob() == null) {
            throw new IllegalArgumentException("Fields of trader can not be empty or null");
        }
        //Create a new trader, then save into DB
        Trader newTrader = traderDao.save(trader);
        //Create a new account using traderId, then save into DB
        Account account = new Account();
        //account.setId(1); //auto-generated
        account.setTraderId(newTrader.getId());
        account.setAmount(0.0);
        Account newAccount = accountDao.save(account);
        //Create a new traderAccountView, then return
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(newAccount);
        traderAccountView.setTrader(newTrader);
        return traderAccountView;
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        if (!traderDao.existsById(traderId)) {
            throw new IllegalArgumentException("Trader with " + traderId + " doesn't exist!");
        }
        //Check account balance
        Account account = accountDao.findByTraderId(traderId);
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("This account still has fund, cannot delete!");
        }
        //Check positions
        List<Position> positions = positionDao.findByAccountId(account.getId());
        if (positions.stream().anyMatch(position -> position.getPosition() != 0)) {
            throw new IllegalArgumentException("This account still has position, cannot delete!");
        }
        securityOrderDao.deleteByAccountId(account.getId());
        accountDao.deleteById(account.getId());
        traderDao.deleteById(traderId);
    }
}