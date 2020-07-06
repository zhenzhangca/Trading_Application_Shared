package ca.jrvs.apps.trading.service.impl;

import ca.jrvs.apps.trading.excptions.ResourceNotFoundException;
import ca.jrvs.apps.trading.repositoris.AccountRepository;
import ca.jrvs.apps.trading.repositoris.PositionSqlRepository;
import ca.jrvs.apps.trading.repositoris.QuoteRepository;
import ca.jrvs.apps.trading.repositoris.TraderRepository;
import ca.jrvs.apps.trading.repositoris.models.domain.*;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.web.resources.PortfolioResponse;
import ca.jrvs.apps.trading.web.resources.TraderProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("DashBoardServiceImpl")
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private TraderRepository traderRepo;
    @Autowired
    private PositionSqlRepository positionRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private QuoteRepository quoteRepo;

    /**
     * Create and return a trader profile by trader ID
     * - get trader account by id
     * - get trader info by id
     * - create and return a traderAccountView
     *
     * @param traderId trader ID
     * @return traderAccountView
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws DataAccessException       if unable to retrieve data
     * @throws IllegalArgumentException  for invalid input
     */

    public TraderProfileResponse getTraderProfile(Integer traderId) {
        if (!traderRepo.existsById(traderId)) {
            throw new IllegalArgumentException("Invalid trader ID!");
        }
        Account account = accountRepo.findByTraderId(traderId);
        Optional<Trader> result = traderRepo.findById(traderId);
        Trader trader = result.isPresent() ? result.get() : null;
        TraderProfile traderProfile = TraderProfile.builder()
                .account(account)
                .trader(trader)
                .build();
        TraderProfileResponse response = convertTraderProfile(traderProfile);
        return response;
    }

    private TraderProfileResponse convertTraderProfile(TraderProfile model) {
        return TraderProfileResponse.builder()
                .account(model.getAccount())
                .trader(model.getTrader())
                .build();
    }

    /**
     * Create and return portfolioView by trader ID
     * - get account by trader id
     * - get positions by account id
     * - create and return a portfolioView
     *
     * @param traderId
     * @return portfolioView
     * @throws ResourceNotFoundException                   if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public PortfolioResponse getPortfolioByTraderId(Integer traderId) {
        if (!traderRepo.existsById(traderId)) {
            throw new IllegalArgumentException("Invalid trader ID!");
        }
        Account account = accountRepo.findByTraderId(traderId);
        List<Position> positions = positionRepo.findByAccountId(account.getId());

        List<SecurityRow> securityRows = new ArrayList<>();

        positions.stream().forEach(i -> {
            SecurityRow securityRow = SecurityRow.builder()
                    .position(i)
                    .ticker(i.getTicker())
                    .quote(quoteRepo.findById(i.getTicker()).isPresent() ? quoteRepo.findById(i.getTicker()).get() : null)
                    .build();
            securityRows.add(securityRow);
        });
        Portfolio portfolio = Portfolio.builder()
                .securityRows(securityRows)
                .build();
        PortfolioResponse response = convertPortfolio(portfolio);
        return response;
    }

    private PortfolioResponse convertPortfolio(Portfolio model) {
        return PortfolioResponse.builder()
                .securityRows(model.getSecurityRows())
                .build();
    }
}
