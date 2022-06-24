package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnitStatisticResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")
@RestController
public class NodeApiController implements NodeApi {

    private static final Logger log = LoggerFactory.getLogger(NodeApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public NodeApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ShopUnitStatisticResponse> nodeIdStatisticGet(@Parameter(in = ParameterIn.PATH, description = "UUID товара/категории для которой будет отображаться статистика", required = true, schema = @Schema())
                                                                        @PathVariable("id") UUID id,

                                                                        @Parameter(in = ParameterIn.QUERY, description = "Дата и время начала интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.", schema = @Schema())
                                                                        @Valid @RequestParam(value = "dateStart", required = false) LocalDateTime dateStart,

                                                                        @Parameter(in = ParameterIn.QUERY, description = "Дата и время конца интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.", schema = @Schema())
                                                                        @Valid @RequestParam(value = "dateEnd", required = false) LocalDateTime dateEnd) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ShopUnitStatisticResponse>(objectMapper.readValue("{\n  \"items\" : [ {\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a444\",\n    \"name\" : \"Оффер\",\n    \"date\" : \"2022-05-28T21:12:01.000Z\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n    \"price\" : 234,\n    \"type\" : \"OFFER\"\n  }, {\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a444\",\n    \"name\" : \"Оффер\",\n    \"date\" : \"2022-05-28T21:12:01.000Z\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n    \"price\" : 234,\n    \"type\" : \"OFFER\"\n  } ]\n}", ShopUnitStatisticResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ShopUnitStatisticResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ShopUnitStatisticResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
