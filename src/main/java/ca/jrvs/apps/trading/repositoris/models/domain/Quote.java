package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A stock quote is the price of a stock as quoted on an exchange.
 * A basic quote for a specific stock provides information,
 * such as its bid and ask price, last traded price, and volume traded.
 */
@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUOTE")
public class Quote implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1l;
    @Id
    @Column(name = "TICKER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ticker;
    @Column(name = "ASK_PRICE", nullable = false)
    private Double askPrice;
    @Column(name = "ASK_SIZE", nullable = false)
    private Integer askSize;
    @Column(name = "BID_PRICE", nullable = false)
    private Double bidPrice;
    @Column(name = "BID_SIZE", nullable = false)
    private Integer bidSize;
    @Column(name = "LAST_PRICE", nullable = false)
    private Double lastPrice;

}
