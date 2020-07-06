package ca.jrvs.apps.trading.web;

import ca.jrvs.apps.trading.repositoris.models.domain.IexQuote;
import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import ca.jrvs.apps.trading.web.resources.IexQuoteResponse;
import ca.jrvs.apps.trading.web.resources.PortfolioResponse;
import ca.jrvs.apps.trading.web.resources.QuoteResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    /**
     * /quote/iexMarketData
     */

    @PutMapping(path = "/iexMarketData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Update quote table using iex data", notes = "Update all quotes in the quote table. Use IEX trading API as market data source",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, responseContainer = "List", response = QuoteResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes successfully."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public ResponseEntity<?> updateMarketData() {
        try {
            List<QuoteResponse> response = quoteService.updateMarketData();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(response);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Update a given quote in the quote table", notes = "Manually update a quote in the quote table for testing purpose",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = QuoteResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes successfully."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public ResponseEntity<?> putQuote(
            @ApiParam(value = "Quote to be updated.")
            @RequestBody Quote quote) {
        try {
            QuoteResponse response = quoteService.update(quote);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(response);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Add a ticker to dailyList(quote table)", notes = "Init a new ticker in the dailyList")
    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteService.initQuote(tickerId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/dailyList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Show the dailyList", notes = "Show dailyList for this trading system",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, responseContainer = "List", response = QuoteResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes sucessfully."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public ResponseEntity<?> getDailyList() {
        try {
            List<QuoteResponse> response = quoteService.getDailyList();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(response);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/iex/ticker/{ticker}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Show iexQuote", notes =  "Show iexQuote for a given ticker or symbol",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = IexQuoteResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes sucessfully."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public ResponseEntity<?> getQuote(
            @ApiParam(value = "Ticker")
            @PathVariable String ticker) {
        try {
            IexQuoteResponse response = quoteService.findIexQuoteByTicker(ticker);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(response);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}