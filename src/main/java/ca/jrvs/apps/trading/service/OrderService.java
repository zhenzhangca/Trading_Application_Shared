package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    public SecurityOrder executeMarketOrder(MarketOrderDto marketOrderDto) {
        if (marketOrderDto == null || marketOrderDto.getSize() == 0) {
            throw new IllegalArgumentException("Invalid order size");
        }
        //Init order
        SecurityOrder securityOrder = new SecurityOrder();
        Quote quote = quoteDao.findById(marketOrderDto.getTicker());
        if (quote == null) {
            throw new IllegalArgumentException(marketOrderDto.getTicker() + " is not in the system");
        }
        securityOrder.setAccountId(marketOrderDto.getAccountId());
        securityOrder.setTicker(marketOrderDto.getTicker());
        //Check ask size
        securityOrder.setSize(marketOrderDto.getSize());
        Account account = accountDao.findByIdForUpdate(marketOrderDto.getAccountId());
        //Handle buy or sell order
        if (marketOrderDto.getSize() > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(marketOrderDto, securityOrder, account);
        } else {
            //Check position for sell order
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(marketOrderDto, securityOrder, account);
        }
        return securityOrderDao.save(securityOrder);
    }

    /**
     * @throws java.sql.SQLException if failed to fetch data from DB
     */
    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        //Advanced SELECT for UPDATE
        Double buyPower = marketOrderDto.getSize() * securityOrder.getPrice();
        if (account.getAmount() >= buyPower) {
            double updateAmount = account.getAmount() - buyPower;
            accountDao.updateAmountById(marketOrderDto.getAccountId(), updateAmount);
            securityOrder.setStatus(OrderStatus.FILLED);
        } else {
            securityOrder.setStatus(OrderStatus.CANCELED);
            securityOrder.setNotes("Insufficient fund. Require buyPower: " + buyPower);
        }
    }

    /**
     * ISSUE: VIEW doesn't support 'SELECT FOR UPDATE'...
     *
     * @throws java.sql.SQLException if failed to fetch data from DB
     */
    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        Long position = positionDao.findByIdAndTicker(marketOrderDto.getAccountId(), marketOrderDto.getTicker());
        logger.debug("AccountId: " + marketOrderDto.getAccountId() + " has position: " + position);
        if (position + marketOrderDto.getSize() >= 0) {
            Double sellAmount = -securityOrder.getSize() * securityOrder.getPrice();
            double updateAmount = account.getAmount() + sellAmount;
            securityOrder.setStatus(OrderStatus.FILLED);
            accountDao.updateAmountById(marketOrderDto.getAccountId(), updateAmount);
        } else {
            securityOrder.setStatus(OrderStatus.CANCELED);
            securityOrder.setNotes("Insufficient position");
        }
    }
}
