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
@IdClass(SecurityOrderId.class)
@Table(name = "SECURITY_ORDER")
public class SecurityOrder implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1l;
    /**
     * see schema, ID must be not null & auto increment, or when save will see "ID could not be null" exception
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Integer accountId;
    @Column(name = "STATUS", nullable = false)
    private String status;
    @Id
    @Column(name = "TICKER", nullable = false)
    private String ticker;
    @Column(name = "NOTES", nullable = false)
    private String notes;
    @Column(name = "PRICE", nullable = false)
    private Double price;
    @Column(name = "SIZE", nullable = false)
    private Integer size;

}
