package ca.jrvs.apps.trading.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping(value = "/api/v1/trading")
@Api(value = "ApplicationTest")
@Slf4j
public class AppController {
    /**
     * Test if the trading app is successfully running.
     * <p>
     * User sent http request with the uri: "localhost:8080/health", then receive the String "I am very healthy!"
     *
     * @return String http response
     */
    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Test if the trading app is successfully running", notes = "Test if the trading app is successfully running.",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completes sucessfully."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    public String health() {
        return "I am very healthy!";
    }
}
