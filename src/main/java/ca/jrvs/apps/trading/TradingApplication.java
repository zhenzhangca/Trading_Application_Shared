package ca.jrvs.apps.trading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingApplication {
    private Logger logger = LoggerFactory.getLogger(TradingApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TradingApplication.class, args);
    }

}
