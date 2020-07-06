package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.web.resources.MarketOrderRequest;
import ca.jrvs.apps.trading.web.resources.SecurityOrderResponse;

public interface OrderService {
    SecurityOrderResponse addMarketOrder(MarketOrderRequest req);
}
