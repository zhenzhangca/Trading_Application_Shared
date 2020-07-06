package ca.jrvs.apps.trading.web.resources;

import ca.jrvs.apps.trading.repositoris.models.domain.Account;
import ca.jrvs.apps.trading.repositoris.models.domain.Trader;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraderProfileResponse {
    private Account account;
    private Trader trader;
}
