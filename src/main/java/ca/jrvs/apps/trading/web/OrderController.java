package ca.jrvs.apps.trading.web;

import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import ca.jrvs.apps.trading.web.resources.MarketOrderRequest;
import ca.jrvs.apps.trading.web.resources.PortfolioResponse;
import ca.jrvs.apps.trading.web.resources.SecurityOrderResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/v1/trading/order")
@Api(value = "order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * /order/marketOrder
     */

    @PostMapping(path = "/marketOrder", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Submit a market order", notes = "Submit a market order, then return the result securityOrder.",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = PortfolioResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes successfully."),
            @ApiResponse(code = 404, message = "AccountId or Ticker is invalid."),
            @ApiResponse(code = 404, message = "Unable to post due to input."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public ResponseEntity<?> addMarketOrder(
            @ApiParam("MarketOrderRequest, BUY with positive size value, SELL with negative size value")
            @Valid @RequestBody MarketOrderRequest req) {
        try {
            SecurityOrderResponse response = orderService.addMarketOrder(req);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(response);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
