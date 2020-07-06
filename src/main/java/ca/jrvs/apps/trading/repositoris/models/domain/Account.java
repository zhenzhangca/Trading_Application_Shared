package ca.jrvs.apps.trading.repositoris.models.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1l;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    @Column(name = "TRADER_ID", nullable = false)
    private Integer traderId;

}