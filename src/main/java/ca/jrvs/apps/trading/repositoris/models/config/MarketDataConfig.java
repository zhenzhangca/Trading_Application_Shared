package ca.jrvs.apps.trading.repositoris.models.config;

import lombok.*;


@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketDataConfig {
    private String host;
    private String token;

}
