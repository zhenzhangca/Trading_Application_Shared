package ca.jrvs.apps.trading.service.impl;

import ca.jrvs.apps.trading.repositoris.AccountRepository;
import ca.jrvs.apps.trading.repositoris.PositionSqlRepository;
import ca.jrvs.apps.trading.repositoris.QuoteRepository;
import ca.jrvs.apps.trading.repositoris.SecurityOrderRepository;
import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import ca.jrvs.apps.trading.repositoris.models.domain.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.OrderStatus;
import ca.jrvs.apps.trading.web.resources.MarketOrderRequest;
import ca.jrvs.apps.trading.web.resources.SecurityOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("OrderServiceImpl")
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private SecurityOrderRepository securityOrderRepo;
    @Autowired
    private QuoteRepository quoteRepo;
    @Autowired
    private PositionSqlRepository positionRepo;

    public SecurityOrderResponse addMarketOrder(MarketOrderRequest req) {
        if (req == null || req.getSize() == 0) {
            throw new IllegalArgumentException("Invalid order size");
        }
        //Init order
        SecurityOrder securityOrder = SecurityOrder.builder()
                .accountId(req.getAccountId())
                .ticker(req.getTicker())
                .size(req.getSize())
                .build();
        Optional<Quote> quoteResult = quoteRepo.findById(req.getTicker());
        Quote quote = null;
        if (!quoteResult.isPresent()) {
            throw new IllegalArgumentException(req.getTicker() + " does not exist in the system!");
        } else {
            quote = quoteResult.get();
        }
        //Check ask size
        Optional<Account> accountResult = accountRepo.findById(req.getAccountId());
        Account account = accountResult.isPresent() ? accountResult.get() : null;
        //Handle buy or sell order
        if (req.getSize() > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(req, securityOrder, account);
        } else {
            //Check position for sell order
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(req, securityOrder, account);
        }
        SecurityOrder savedSecurityOrder = securityOrderRepo.save(securityOrder);
        return convertSecurityOrder(savedSecurityOrder);
    }

    private SecurityOrderResponse convertSecurityOrder(SecurityOrder model) {
        return SecurityOrderResponse.builder()
                .id(model.getId())
                .accountId(model.getAccountId())
                .status(model.getStatus())
                .ticker(model.getTicker())
                .notes(model.getNotes())
                .price(model.getPrice())
                .size(model.getSize())
                .build();
    }

    /**
     * @throws java.sql.SQLException if failed to fetch data from DB
     */
    protected void handleBuyMarketOrder(MarketOrderRequest req, SecurityOrder securityOrder, Account account) {
        //Advanced SELECT for UPDATE
        Double buyPower = req.getSize() * securityOrder.getPrice();
        if (account.getAmount() >= buyPower) {
            double updateAmount = account.getAmount() - buyPower;
            accountRepo.updateAmountById(req.getAccountId(), updateAmount);
            securityOrder.setStatus(OrderStatus.FILLED);
        } else {
            securityOrder.setStatus(OrderStatus.CANCELLED);
            securityOrder.setNotes("Insufficient fund. Require buyPower: " + buyPower);
        }
    }

    /**
     * ISSUE: VIEW doesn't support 'SELECT FOR UPDATE'...
     *
     * @throws java.sql.SQLException if failed to fetch data from DB
     */
    protected void handleSellMarketOrder(MarketOrderRequest req, SecurityOrder securityOrder, Account account) {
//        Long position = positionRepo.findByIdAndTicker(req.getAccountId(), req.getTicker());
//        log.debug("AccountId: " + req.getAccountId() + " has position: " + position);
//        if (position + req.getSize() >= 0) {
//            Double sellAmount = -securityOrder.getSize() * securityOrder.getPrice();
//            double updateAmount = account.getAmount() + sellAmount;
//            securityOrder.setStatus(OrderStatus.FILLED);
//            accountRepo.updateAmountById(req.getAccountId(), updateAmount);
//        } else {
//            securityOrder.setStatus(OrderStatus.CANCELLED);
//            securityOrder.setNotes("Insufficient position");
//        }
    }
}
