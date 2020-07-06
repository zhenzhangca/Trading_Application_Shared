package ca.jrvs.apps.trading.web.resources;

import lombok.*;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse{

    private Integer id;
    private Double amount;
    private Integer traderId;
}
