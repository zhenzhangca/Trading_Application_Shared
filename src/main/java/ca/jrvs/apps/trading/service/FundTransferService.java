package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.web.resources.AccountResponse;

public interface FundTransferService {
    AccountResponse deposit(Integer traderId, Double fund);
    AccountResponse withdraw(Integer traderId, Double fund);
}
