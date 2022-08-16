package mega.market.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


/**
 * Error
 */
@Value
public class ErrorDto {
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;
}
