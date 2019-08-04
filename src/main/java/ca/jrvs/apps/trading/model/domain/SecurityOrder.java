package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "id",
        "notes",
        "price",
        "size",
        "status",
        "ticker"
})
public class SecurityOrder implements Entity<Integer> {

    @JsonProperty("accountId")
    private Integer accountId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("size")
    private Integer size;

    private OrderStatus status;
    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("accountId")
    public Integer getAccountId() {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "SecurityOrder{" +
                "accountId=" + accountId +
                ", id=" + id +
                ", notes='" + notes + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", status=" + status +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
