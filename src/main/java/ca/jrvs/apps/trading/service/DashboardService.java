package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.repositoris.models.domain.Portfolio;
import ca.jrvs.apps.trading.repositoris.models.domain.TraderProfile;
import ca.jrvs.apps.trading.web.resources.PortfolioResponse;
import ca.jrvs.apps.trading.web.resources.TraderProfileResponse;

public interface DashboardService {

    PortfolioResponse getPortfolioByTraderId(Integer traderId);

    TraderProfileResponse getTraderProfile(Integer traderId);
}
