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
public class StockResponse extends BaseResponse {

    @JsonProperty("stock_id")
    private Long stockId;

    @JsonProperty("stock_name")
    private String stockName;

    @JsonProperty("stock_ticker")
    private String stockTicker;

    @JsonProperty("stock_price")
    private Double stockPrice;

    @JsonProperty("available_qty")
    private Integer availableQty;
}