package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityOrderId implements Serializable {
    private Integer accountId;
    private String ticker;
}
