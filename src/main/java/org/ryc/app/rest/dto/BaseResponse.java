package org.ryc.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}