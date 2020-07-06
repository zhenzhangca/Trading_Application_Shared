package ca.jrvs.apps.trading.web.resources;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityOrderResponse {

    private Integer id;
    private Integer accountId;
    private String status;
    private String ticker;
    private String notes;
    private Double price;
    private Integer size;
}
