package ca.jrvs.apps.trading.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "App", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/")
public class AppController {
    /**
     * Test if the trading app is successfully running.
     * <p>
     * User sent http request with the uri "localhost:8080/health", then receive the String "I am very healthy!"
     *
     * @return String http response
     */
    @GetMapping(path = "/health")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String health() {
        return "I am very healthy!";
    }
}
