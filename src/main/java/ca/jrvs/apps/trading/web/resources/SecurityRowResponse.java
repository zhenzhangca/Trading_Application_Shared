package ca.jrvs.apps.trading.web.resources;

import ca.jrvs.apps.trading.repositoris.models.domain.Position;
import ca.jrvs.apps.trading.repositoris.models.domain.Quote;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityRowResponse {
    private Position position;
    private Quote quote;
    private String ticker;
}
