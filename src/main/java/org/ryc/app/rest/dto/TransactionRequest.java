package org.ryc.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class TransactionRequest extends BaseRequest {

    @JsonProperty("stock_id")
    private Long stockId;

    @JsonProperty("stock_name")
    private String stockName;

    @JsonProperty("stock_ticker")
    private String stockTicker;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("total_value")
    private Double totalValue;
}