package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityRow {
    private Position position;
    private Quote quote;
    private String ticker;
}
