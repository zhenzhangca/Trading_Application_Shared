package ca.jrvs.apps.trading.web.resources;

import ca.jrvs.apps.trading.repositoris.models.domain.SecurityRow;
import lombok.*;

import java.util.List;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    private List<SecurityRow> securityRows;
}
