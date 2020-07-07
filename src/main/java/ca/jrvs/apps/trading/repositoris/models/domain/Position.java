package ca.jrvs.apps.trading.repositoris.models.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    private Integer accountId;

    private Long position;

    private String ticker;

}