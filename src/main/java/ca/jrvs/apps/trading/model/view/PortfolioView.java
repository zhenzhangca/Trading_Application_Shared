package ca.jrvs.apps.trading.model.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "securityRows"
})
public class PortfolioView {

    @JsonProperty("securityRows")
    private List<SecurityRow> securityRows;

    @JsonProperty("securityRows")
    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    @JsonProperty("securityRows")
    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }
}
