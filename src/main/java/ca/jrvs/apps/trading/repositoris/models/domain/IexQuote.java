package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IexQuote {

    private String avgTotalVolume;
    private String calculationPrice;
    private String change;
    private String changePercent;
    private String close;
    private String closeTime;
    private String companyName;
    private String delayedPrice;
    private String delayedPriceTime;
    private String extendedChange;
    private String extendedChangePercent;
    private String extendedPrice;
    private String extendedPriceTime;
    private String high;
    private String iexAskPrice;
    private String iexAskSize;
    private String iexBidPrice;
    private String iexBidSize;
    private String iexLastUpdated;
    private String iexMarketPercent;
    private String iexRealtimePrice;
    private String iexRealtimeSize;
    private String iexVolume;
    private String latestPrice;
    private String latestSource;
    private String latestTime;
    private String latestUpdate;
    private String latestVolume;
    private String low;
    private String marketCap;
    private String open;
    private String openTime;
    private String peRatio;
    private String previousClose;
    private String primaryExchange;
    private String sector;
    private String symbol;
    private String week52High;
    private String week52Low;
    private String ytdChange;

}