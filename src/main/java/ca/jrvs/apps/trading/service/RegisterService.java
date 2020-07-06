package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.web.resources.TraderProfileResponse;
import ca.jrvs.apps.trading.web.resources.TraderRequest;

public interface RegisterService {

    TraderProfileResponse createTraderAndAccount(TraderRequest req);

    void deleteTraderById(Integer traderId);
}
