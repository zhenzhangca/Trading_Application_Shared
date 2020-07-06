package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraderProfile {

    private Account account;
    private Trader trader;
}
