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
@Table(name = "SECURITY_ORDER")
public class SecurityOrder implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1l;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Integer accountId;
    @Column(name = "STATUS", nullable = false)
    private String status;
    @Column(name = "TICKER", nullable = false)
    private String ticker;
    @Column(name = "NOTES", nullable = false)
    private String notes;
    @Column(name = "PRICE", nullable = false)
    private Double price;
    @Column(name = "SIZE", nullable = false)
    private Integer size;

}
