package ca.jrvs.apps.trading.web.resources;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRequest {

    private String ticker;
    private Double askPrice;
    private Integer askSize;
    private Double bidPrice;
    private Integer bidSize;
    private Double lastPrice;
}
