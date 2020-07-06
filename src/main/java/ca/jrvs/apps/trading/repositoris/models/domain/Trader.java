package ca.jrvs.apps.trading.repositoris.models.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRADER")
public class Trader implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1l;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "COUNTRY", nullable = false)
    private String country;
    @Column(name = "DOB", nullable = false)
    private Date dob;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;


}
