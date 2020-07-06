//package ca.jrvs.apps.trading.web;
//
//import ca.jrvs.apps.trading.repositoris.models.domain.IexQuote;
//import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
//import ca.jrvs.apps.trading.service.QuoteService;
//import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//
//@Api(value = "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//@Controller
//@RequestMapping("/quote")
//public class QuoteController {
//    @Autowired
//    private QuoteService quoteService;
//
////    @Autowired
////    private QuoteRepository quoteRepo;
////    @Autowired
////    private MarketDataRepository marketDataRepo;
//
//    /**
//     * /quote/iexMarketData
//     */
//    @ApiOperation(value = "Update quote table using iex data", notes = "Update all quotes in the quote table. Use IEX trading API as market data source")
//    @PutMapping(path = "/iexMarketData")
//    @ResponseStatus(HttpStatus.OK)
//    public void updateMarketData() {
//        try {
//            quoteService.updateMarketData();
//        } catch (Exception e) {
//            throw ResponseExceptionUtil.getResponseStatusException(e);
//        }
//    }
//
//    @ApiOperation(value = "Update a given quote in the quote table", notes = "Manualy update a quote in the quote table for testing purpose")
//    @PutMapping(path = "/")
//    @ResponseStatus(HttpStatus.OK)
//    public void putQuote(@RequestBody Quote quote) {
//        try {
//            quoteDao.update(Collections.singletonList(quote));
//        } catch (Exception e) {
//            throw ResponseExceptionUtil.getResponseStatusException(e);
//        }
//    }
//
//    @ApiOperation(value = "Add a ticker to dailyList(quote table)", notes = "Init a new ticker in the dailyList")
//    @PostMapping(path = "/tickerId/{tickerId}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createQuote(@PathVariable String tickerId) {
//        try {
//            quoteService.initQuote(tickerId);
//        } catch (Exception e) {
//            throw ResponseExceptionUtil.getResponseStatusException(e);
//        }
//    }
//
//    @ApiOperation(value = "Show the dailyList", notes = "Show dailyList for this trading system")
//    @GetMapping(path = "/dailyList")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public List<Quote> getDailyList() {
//        try {
//            return quoteDao.findAll();
//        } catch (Exception e) {
//            throw ResponseExceptionUtil.getResponseStatusException(e);
//        }
//    }
//
//    @ApiOperation(value = "Show iexQuote", notes = "Show iexQuote for a given ticker or symbol")
//    @ApiResponses(value = {@ApiResponse(code = 404, message = "ticker is not found")})
//    @GetMapping(path = "/iex/ticker/{ticker}")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public IexQuote getQuote(@PathVariable String ticker) {
//        try {
//            return marketDataDao.findIexQuoteByTicker(ticker);
//        } catch (Exception e) {
//            throw ResponseExceptionUtil.getResponseStatusException(e);
//        }
//    }
//}