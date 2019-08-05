package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Api(value = "trader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/trader")
public class TraderController {
    private FundTransferService fundTransferService;
    private RegisterService registerService;

    @Autowired
    public TraderController(FundTransferService fundTransferService, RegisterService registerService) {
        this.fundTransferService = fundTransferService;
        this.registerService = registerService;
    }

    @ApiOperation(value = "Delete a trader", notes = "Delete a trader IFF its account amount is 0 and no open positions. Also delete the associated account and securityOrders.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to delete the user")})
    @DeleteMapping(path = "/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId) {
        try {
            registerService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Create a trader and an account with DTO", notes = "TraderId and AccountId are auto generated by the database, and they should be identical. Assume each trader has exact one account.")
    @PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TraderAccountView createTrader(@RequestBody Trader trader) {
        try {
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Create a trader and an account", notes = "TraderId and AccountId are auto generated by the database, and they should be identical. Assume each trader has exact one account.")
    @PostMapping(path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TraderAccountView createTrader(@PathVariable String firstname, @PathVariable String lastname, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob, @PathVariable String country, @PathVariable String email) {
        try {
            Trader trader = new Trader();
            trader.setFirstName(firstname);
            trader.setLastName(lastname);
            trader.setCountry(country);
            trader.setEmail(email);
            trader.setDob(Date.valueOf(dob));
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Deposit a fund", notes = "Deposit a fund to the account that associates with the given traderId. Deposit amount must be greater than 0.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "traderId is not found"),
            @ApiResponse(code = 400, message = "Unable to deposit due to user input")
    })
    @PutMapping(path = "/deposit/traderId/{traderId}/amonut/{amount}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account depositFund(@PathVariable Double amount, @PathVariable Integer traderId) {
        try {
            return fundTransferService.deposit(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Withdraw a fund", notes = "Withdraw a fund from the account that associates with the given traderId. Withdraw amount must not exceed account amount.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "traderId is not found"),
            @ApiResponse(code = 400, message = "Unable to withdraw due to user input (e.g. insufficient funds")
    })
    @PutMapping(path = "/withdraw/traderId/{traderId}/amonut/{amount}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account withdrawFund(@PathVariable Double amount, @PathVariable Integer traderId) {
        try {
            return fundTransferService.withdraw(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}