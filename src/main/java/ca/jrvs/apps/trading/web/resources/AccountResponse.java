package ca.jrvs.apps.trading.web.resources;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class AccountResponse{

    private Integer id;
    private Double amount;
    private Integer traderId;
}
