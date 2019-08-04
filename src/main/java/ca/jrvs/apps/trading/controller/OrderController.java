package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * /order/marketOrder
     */
    @ApiOperation(value = "Submit a market order", notes = "Submit a market order, then return the result securityOrder.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "accountId or ticker is invalid"),
            @ApiResponse(code = 400, message = "Unable to post due to input")
    })
    @PostMapping(path = "/marketOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityOrder postOrder(@RequestBody MarketOrderDto orderDto) {
        try {
            return orderService.executeMarketOrder(orderDto);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

}
