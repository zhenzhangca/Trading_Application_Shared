package ca.jrvs.apps.trading.web.resources;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
//@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraderRequest {

    private Integer id;
    private String country;
    private Date dob;
    private String email;
    private String firstName;
    private String lastName;
}