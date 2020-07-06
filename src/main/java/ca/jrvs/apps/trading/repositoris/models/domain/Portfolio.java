package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    private List<SecurityRow> securityRows;
}
